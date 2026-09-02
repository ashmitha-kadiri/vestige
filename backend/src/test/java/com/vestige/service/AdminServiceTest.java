package com.vestige.service;

import com.vestige.dto.request.AdminUserStatusRequest;
import com.vestige.dto.request.AdminVendorVerifyRequest;
import com.vestige.dto.response.UserSummaryResponse;
import com.vestige.dto.response.VendorSummaryResponse;
import com.vestige.exception.ForbiddenException;
import com.vestige.model.User;
import com.vestige.model.VendorProfile;
import com.vestige.model.enums.PreferredLanguage;
import com.vestige.model.enums.UserRole;
import com.vestige.model.enums.VendorVerificationStatus;
import com.vestige.repository.AdminActionRepository;
import com.vestige.repository.UserRepository;
import com.vestige.repository.VendorProfileRepository;
import com.vestige.security.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorProfileRepository vendorProfileRepository;

    @Autowired
    private AdminActionRepository adminActionRepository;

    private UUID adminUuid;
    private String adminEmail;

    @BeforeEach
    void setUp() {
        adminUuid = UUID.randomUUID();
        adminEmail = "overseer." + adminUuid + "@vestige.internal";
        User admin = new User("Archival Overseer", adminEmail, "hash", "+919999999999", UserRole.ADMIN);
        admin.setId(adminUuid);
        userRepository.save(admin);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateAsAdmin() {
        UserPrincipal principal = new UserPrincipal(adminUuid, adminEmail, "Archival Overseer", UserRole.ADMIN, PreferredLanguage.en, true);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("Admin verifies and approves vendor profile successfully")
    void adminApprovesVendor() {
        authenticateAsAdmin();

        UUID vendorUserUuid = UUID.randomUUID();
        User vendorUser = new User("Vendor Contact", "vendor." + vendorUserUuid + "@vestige.internal", "hash", "+919888800000", UserRole.VENDOR);
        vendorUser.setId(vendorUserUuid);
        userRepository.save(vendorUser);

        VendorProfile profile = new VendorProfile();
        profile.setUser(vendorUser);
        profile.setBusinessName("Pending Workshop Co");
        profile.setAddress("44 Forge Lane");
        profile.setCity("Bengaluru");
        profile.setState("Karnataka");
        profile.setPincode("560002");
        profile.setVerificationStatus(VendorVerificationStatus.PENDING);
        VendorProfile savedProfile = vendorProfileRepository.save(profile);

        AdminVendorVerifyRequest req = new AdminVendorVerifyRequest("APPROVE", null);
        VendorSummaryResponse response = adminService.verifyVendor(savedProfile.getId(), req, "127.0.0.1");

        assertNotNull(response);
        assertEquals(VendorVerificationStatus.VERIFIED, response.getVerificationStatus());

        // Verify audit log
        var logs = adminActionRepository.findByAdminIdOrderByCreatedAtDesc(adminUuid);
        assertFalse(logs.isEmpty());
        assertEquals("APPROVE_VENDOR", logs.get(0).getActionType());
    }

    @Test
    @DisplayName("Admin suspends user and logs action to audit trail")
    void adminSuspendsUser() {
        authenticateAsAdmin();

        UUID targetUserUuid = UUID.randomUUID();
        User user = new User("Target Patron", "target." + targetUserUuid + "@vestige.internal", "hash", "+919777777777", UserRole.USER);
        user.setId(targetUserUuid);
        user.setActive(true);
        userRepository.save(user);

        AdminUserStatusRequest req = new AdminUserStatusRequest(false, "Suspicious repeated bookings");
        UserSummaryResponse response = adminService.updateUserStatus(targetUserUuid, req, "127.0.0.1");

        assertNotNull(response);
        assertFalse(response.isActive());

        // Verify audit log
        var logs = adminActionRepository.findByAdminIdOrderByCreatedAtDesc(adminUuid);
        assertFalse(logs.isEmpty());
        assertEquals("SUSPEND_USER", logs.get(0).getActionType());
    }

    @Test
    @DisplayName("Admin retrieves all repairs, recycling, rewards, and registration activity")
    void adminRetrievesAllLedgersAndRegistrations() {
        authenticateAsAdmin();

        assertNotNull(adminService.getAllRepairs());
        assertNotNull(adminService.getAllRecycling());
        assertNotNull(adminService.getAllRewardAccounts());

        var regActivity = adminService.getRegistrationActivity("6m");
        assertNotNull(regActivity);
        assertNotNull(regActivity.getTimeline());
        assertNotNull(regActivity.getComparisonMessage());
    }

    @Test
    @DisplayName("Non-admin user calling admin operations is rejected with ForbiddenException")
    void nonAdminCallingAdminFails() {
        UUID regularId = UUID.randomUUID();
        UserPrincipal principal = new UserPrincipal(regularId, "user." + regularId + "@vestige.internal", "Regular User", UserRole.USER, PreferredLanguage.en, true);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));

        assertThrows(ForbiddenException.class, () -> adminService.getAllUsers());
        assertThrows(ForbiddenException.class, () -> adminService.getAllRepairs());
        assertThrows(ForbiddenException.class, () -> adminService.getRegistrationActivity("30d"));
    }
}

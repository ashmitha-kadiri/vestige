package com.vestige.service;

import com.vestige.dto.request.AdminProvisionRequest;
import com.vestige.dto.request.UserRegisterRequest;
import com.vestige.dto.request.VendorRegisterRequest;
import com.vestige.dto.response.AuthMeResponse;
import com.vestige.dto.response.UserSummaryResponse;
import com.vestige.exception.ForbiddenException;
import com.vestige.model.User;
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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorProfileRepository vendorProfileRepository;

    @Autowired
    private AdminActionRepository adminActionRepository;

    private UUID userUuid;
    private UUID adminUuid;
    private String adminEmail;

    @BeforeEach
    void setUp() {
        userUuid = UUID.randomUUID();
        adminUuid = UUID.randomUUID();
        adminEmail = "admin." + adminUuid + "@vestige.internal";

        // Create an admin user
        User admin = new User("Master Admin", adminEmail, "hash", "+919000000000", UserRole.ADMIN);
        admin.setId(adminUuid);
        userRepository.save(admin);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void authenticate(UUID id, String email, UserRole role) {
        UserPrincipal principal = new UserPrincipal(id, email, "Test User", role, PreferredLanguage.en, true);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("registerUser creates user profile with role USER and initializes reward account")
    void registerUserSuccess() {
        String email = "patron." + userUuid + "@vestige.internal";
        authenticate(userUuid, email, UserRole.USER);

        UserRegisterRequest req = new UserRegisterRequest("Heritage Patron", "+919876543210", PreferredLanguage.ta);
        AuthMeResponse response = authService.registerUser(req);

        assertNotNull(response);
        assertEquals(userUuid, response.getId());
        assertEquals("Heritage Patron", response.getFullName());
        assertEquals(UserRole.USER, response.getRole());
        assertEquals(PreferredLanguage.ta, response.getPreferredLanguage());
        assertNotNull(response.getRewardBalance());
    }

    @Test
    @DisplayName("registerVendor creates vendor profile with PENDING verification status")
    void registerVendorSuccess() {
        UUID vendorUserUuid = UUID.randomUUID();
        String vendorEmail = "vendor." + vendorUserUuid + "@vestige.internal";
        authenticate(vendorUserUuid, vendorEmail, UserRole.VENDOR);

        VendorRegisterRequest req = new VendorRegisterRequest();
        req.setFullName("Craftsman Leader");
        req.setPhone("+919888877770");
        req.setBusinessName("Old World Electronics Atelier");
        req.setAddress("10 Heritage Lane");
        req.setCity("Bengaluru");
        req.setState("Karnataka");
        req.setPincode("560001");
        req.setServiceTypes(List.of("REPAIR"));
        req.setDeviceCategories(List.of("SMARTPHONE", "LAPTOP"));

        AuthMeResponse response = authService.registerVendor(req);

        assertNotNull(response);
        assertEquals(UserRole.VENDOR, response.getRole());
        assertNotNull(response.getVendorProfile());
        assertEquals("Old World Electronics Atelier", response.getVendorProfile().getBusinessName());
        assertEquals(VendorVerificationStatus.PENDING, response.getVendorProfile().getVerificationStatus());
    }

    @Test
    @DisplayName("provisionAdmin succeeds when invoked by ADMIN and logs to admin_actions")
    void provisionAdminByAdminSucceeds() {
        // Create a regular user first
        UUID targetId = UUID.randomUUID();
        User target = new User("Candidate User", "candidate." + targetId + "@vestige.internal", "hash", "+919111111111", UserRole.USER);
        target.setId(targetId);
        userRepository.save(target);

        // Authenticate as Admin
        authenticate(adminUuid, adminEmail, UserRole.ADMIN);

        AdminProvisionRequest req = new AdminProvisionRequest(targetId, "Appointed as Regional Supervisor");
        UserSummaryResponse response = authService.provisionAdmin(req, "127.0.0.1");

        assertNotNull(response);
        assertEquals(UserRole.ADMIN, response.getRole());

        // Check admin_actions table has logged this
        var actions = adminActionRepository.findByAdminIdOrderByCreatedAtDesc(adminUuid);
        assertFalse(actions.isEmpty());
        assertEquals("PROVISION_ADMIN", actions.get(0).getActionType());
    }

    @Test
    @DisplayName("provisionAdmin fails with ForbiddenException when invoked by non-ADMIN")
    void provisionAdminByNonAdminFails() {
        UUID regularUserId = UUID.randomUUID();
        authenticate(regularUserId, "regular." + regularUserId + "@vestige.internal", UserRole.USER);

        AdminProvisionRequest req = new AdminProvisionRequest(UUID.randomUUID(), "Unauthorized attempt");
        assertThrows(ForbiddenException.class, () -> authService.provisionAdmin(req, "127.0.0.1"));
    }

    @Test
    @DisplayName("login succeeds with valid credentials and issues token with correct role")
    void testLoginSuccess() {
        com.vestige.dto.request.PublicUserRegisterRequest regReq = new com.vestige.dto.request.PublicUserRegisterRequest(
                "Login Test User",
                "logintest." + UUID.randomUUID() + "@vestige.org",
                "Password@123",
                "+919876543211",
                PreferredLanguage.en
        );
        com.vestige.dto.response.LoginResponse regRes = authService.registerPublicUser(regReq);
        assertNotNull(regRes.getToken());

        com.vestige.dto.request.LoginRequest loginReq = new com.vestige.dto.request.LoginRequest(
                regReq.getEmail(),
                "Password@123",
                "USER"
        );
        com.vestige.dto.response.LoginResponse loginRes = authService.login(loginReq);
        assertNotNull(loginRes);
        assertNotNull(loginRes.getToken());
        assertEquals(UserRole.USER, loginRes.getProfile().getRole());
    }

    @Test
    @DisplayName("login rejects wrong password with UnauthorizedException")
    void testLoginWrongPassword() {
        com.vestige.dto.request.PublicUserRegisterRequest regReq = new com.vestige.dto.request.PublicUserRegisterRequest(
                "Wrong Password Test",
                "wrongpass." + UUID.randomUUID() + "@vestige.org",
                "CorrectPassword@123",
                "+919876543212",
                PreferredLanguage.en
        );
        authService.registerPublicUser(regReq);

        com.vestige.dto.request.LoginRequest loginReq = new com.vestige.dto.request.LoginRequest(
                regReq.getEmail(),
                "IncorrectPassword",
                "USER"
        );
        assertThrows(com.vestige.exception.UnauthorizedException.class, () -> authService.login(loginReq));
    }

    @Test
    @DisplayName("login rejects role mismatch (USER trying to access ADMIN portal)")
    void testLoginRoleMismatch() {
        com.vestige.dto.request.PublicUserRegisterRequest regReq = new com.vestige.dto.request.PublicUserRegisterRequest(
                "Role Mismatch User",
                "rolemismatch." + UUID.randomUUID() + "@vestige.org",
                "Password@123",
                "+919876543213",
                PreferredLanguage.en
        );
        authService.registerPublicUser(regReq);

        com.vestige.dto.request.LoginRequest loginReq = new com.vestige.dto.request.LoginRequest(
                regReq.getEmail(),
                "Password@123",
                "ADMIN" // Expecting ADMIN portal, but user is USER
        );
        assertThrows(com.vestige.exception.ForbiddenException.class, () -> authService.login(loginReq));
    }

    @Test
    @DisplayName("registerPublicUser rejects duplicate email with ConflictException")
    void testRegisterDuplicateEmail() {
        String email = "duplicate." + UUID.randomUUID() + "@vestige.org";
        com.vestige.dto.request.PublicUserRegisterRequest req1 = new com.vestige.dto.request.PublicUserRegisterRequest(
                "First User",
                email,
                "Password@123",
                "+919876543214",
                PreferredLanguage.en
        );
        authService.registerPublicUser(req1);

        com.vestige.dto.request.PublicUserRegisterRequest req2 = new com.vestige.dto.request.PublicUserRegisterRequest(
                "Second User",
                email,
                "Password@123",
                "+919876543215",
                PreferredLanguage.en
        );
        assertThrows(com.vestige.exception.ConflictException.class, () -> authService.registerPublicUser(req2));
    }
}

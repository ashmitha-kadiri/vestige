package com.vestige.service;

import com.vestige.dto.admin.performance.AdminPerformanceDTO;
import com.vestige.exception.ForbiddenException;
import com.vestige.model.User;
import com.vestige.model.enums.PreferredLanguage;
import com.vestige.model.enums.UserRole;
import com.vestige.repository.UserRepository;
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
class AdminPerformanceServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    private UUID adminUuid;
    private String adminEmail;
    private UUID userUuid;
    private String userEmail;

    @BeforeEach
    void setUp() {
        adminUuid = UUID.randomUUID();
        adminEmail = "overseer." + adminUuid + "@vestige.internal";
        User admin = new User("Archival Overseer", adminEmail, "hash", "+919999999999", UserRole.ADMIN);
        admin.setId(adminUuid);
        userRepository.save(admin);

        userUuid = UUID.randomUUID();
        userEmail = "patron." + userUuid + "@vestige.internal";
        User patron = new User("Standard Patron", userEmail, "hash", "+919888811111", UserRole.USER);
        patron.setId(userUuid);
        userRepository.save(patron);
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

    private void authenticateAsUser() {
        UserPrincipal principal = new UserPrincipal(userUuid, userEmail, "Standard Patron", UserRole.USER, PreferredLanguage.en, true);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("Admin can retrieve full performance analytics across 30d period")
    void testGetAdminPerformanceAsAdmin() {
        authenticateAsAdmin();

        AdminPerformanceDTO result = adminService.getAdminPerformance("30d");

        assertNotNull(result);
        assertNotNull(result.getBusinessKpis());
        assertTrue(result.getBusinessKpis().getTotalUsers() >= 1);
        assertNotNull(result.getGrowthComparison());
        assertEquals("30d", result.getGrowthComparison().getRange());
        assertFalse(result.getGrowthComparison().getUserTimeline().isEmpty());
        assertFalse(result.getGrowthComparison().getVendorTimeline().isEmpty());

        assertNotNull(result.getRepairPerformance());
        assertNotNull(result.getRecyclingPerformance());
        assertNotNull(result.getRewardPerformance());
        assertNotNull(result.getPaymentPerformance());

        // System Performance - Honest Telemetry & DB latency verification
        assertNotNull(result.getSystemPerformance());
        assertEquals("HEALTHY", result.getSystemPerformance().getBackendStatus());
        assertEquals("CONNECTED", result.getSystemPerformance().getDatabaseStatus());
        assertNotNull(result.getSystemPerformance().getDatabaseLatencyMs());
        assertTrue(result.getSystemPerformance().getDatabaseLatencyMs() >= 0);
        assertFalse(result.getSystemPerformance().isTelemetryAvailable(), "Telemetry should honestly report not available without APM");
        assertNotNull(result.getSystemPerformance().getTelemetryNotice());
        assertNotNull(result.getSystemPerformance().getRequiredInfrastructure());
    }

    @Test
    @DisplayName("Admin can retrieve performance analytics for different date range filters (7d, 90d, 12m)")
    void testDateRangeFilters() {
        authenticateAsAdmin();

        AdminPerformanceDTO p7d = adminService.getAdminPerformance("7d");
        assertEquals("7d", p7d.getGrowthComparison().getRange());
        assertEquals(7, p7d.getGrowthComparison().getUserTimeline().size());

        AdminPerformanceDTO p90d = adminService.getAdminPerformance("90d");
        assertEquals("90d", p90d.getGrowthComparison().getRange());

        AdminPerformanceDTO p12m = adminService.getAdminPerformance("12m");
        assertEquals("12m", p12m.getGrowthComparison().getRange());
        assertEquals(12, p12m.getGrowthComparison().getUserTimeline().size());
    }

    @Test
    @DisplayName("Non-admin role (USER) is denied access with ForbiddenException")
    void testNonAdminAccessDenied() {
        authenticateAsUser();

        assertThrows(ForbiddenException.class, () -> adminService.getAdminPerformance("30d"));
    }
}

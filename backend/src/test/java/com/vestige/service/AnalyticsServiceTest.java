package com.vestige.service;

import com.vestige.dto.analytics.*;
import com.vestige.model.*;
import com.vestige.model.enums.*;
import com.vestige.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AnalyticsServiceTest {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorProfileRepository vendorProfileRepository;

    @Autowired
    private DeviceSubmissionRepository deviceSubmissionRepository;

    @Autowired
    private RepairBookingRepository repairBookingRepository;

    @Autowired
    private RecyclingRequestRepository recyclingRequestRepository;

    @Autowired
    private RewardAccountRepository rewardAccountRepository;

    @Autowired
    private RewardTransactionRepository rewardTransactionRepository;

    private User patron;
    private User vendorUser;
    private VendorProfile vendorProfile;
    private DeviceSubmission testSubmission;

    @BeforeEach
    void setUp() {
        patron = new User("Analytics Patron", "patron.analytics@vestige.internal", "pw", "+919999900001", UserRole.USER);
        patron.setActive(true);
        patron = userRepository.save(patron);

        vendorUser = new User("Analytics Craftsman", "craftsman.analytics@vestige.internal", "pw", "+919999900002", UserRole.VENDOR);
        vendorUser.setActive(true);
        vendorUser = userRepository.save(vendorUser);

        vendorProfile = new VendorProfile();
        vendorProfile.setUser(vendorUser);
        vendorProfile.setBusinessName("Atelier Analytics");
        vendorProfile.setBusinessType("REPAIR_WORKSHOP");
        vendorProfile.setAddress("12 Heritage Road");
        vendorProfile.setCity("Bengaluru");
        vendorProfile.setState("Karnataka");
        vendorProfile.setPincode("560001");
        vendorProfile.setServiceTypes(List.of("HARDWARE_REPAIR"));
        vendorProfile.setDeviceCategories(List.of("LAPTOP", "SMARTPHONE"));
        vendorProfile.setVerificationStatus(VendorVerificationStatus.VERIFIED);
        vendorProfile = vendorProfileRepository.save(vendorProfile);

        testSubmission = new DeviceSubmission();
        testSubmission.setUser(patron);
        testSubmission.setDeviceType(DeviceCategoryType.LAPTOP);
        testSubmission.setBrand("Lenovo");
        testSubmission.setModel("ThinkPad");
        testSubmission.setDeviceAgeYears(2);
        testSubmission.setCondition(DeviceConditionGrade.GOOD);
        testSubmission.setEstimatedRepairCost(BigDecimal.valueOf(3500));
        testSubmission.setOriginalValue(BigDecimal.valueOf(60000));
        testSubmission.setPartAvailability(PartAvailabilityStatus.AVAILABLE);
        testSubmission.setEngineScore(78);
        testSubmission.setEngineRecommendation(EngineRecommendationType.REPAIR);
        testSubmission.setEngineConfidence(EngineConfidenceLevel.HIGH);
        testSubmission.setEngineRationale("High repair viability");
        testSubmission = deviceSubmissionRepository.save(testSubmission);

        RewardAccount account = new RewardAccount(patron);
        account.setBalance(150);
        account.setLifetimeEarned(200);
        account.setLifetimeRedeemed(50);
        rewardAccountRepository.save(account);

        RewardTransaction tx = new RewardTransaction(account, 100, RewardTransactionType.EARNED, RewardSourceType.REPAIR_COMPLETION, testSubmission.getId(), "Repair bonus");
        rewardTransactionRepository.save(tx);
    }

    @Test
    @DisplayName("Admin Overview returns populated KPI metrics")
    void testGetAdminOverview() {
        AdminOverviewDTO overview = analyticsService.getAdminOverview(LocalDate.now().minusDays(30), LocalDate.now());
        assertNotNull(overview);
        assertTrue(overview.getTotalUsers() >= 1);
        assertTrue(overview.getActiveUsers() >= 1);
        assertTrue(overview.getTotalVendors() >= 1);
        assertTrue(overview.getVerifiedVendors() >= 1);
        assertTrue(overview.getTotalSubmissions() >= 1);
        assertTrue(overview.getRepairRecommendations() >= 1);
        assertNotNull(overview.getActivityOverTime());
    }

    @Test
    @DisplayName("Device Analytics returns distributions and average scores")
    void testGetAdminDeviceAnalytics() {
        DeviceAnalyticsDTO dto = analyticsService.getAdminDeviceAnalytics(LocalDate.now().minusDays(30), LocalDate.now());
        assertNotNull(dto);
        assertTrue(dto.getTotalSubmissions() >= 1);
        assertEquals(1, dto.getRepairCount());
        assertEquals(0, dto.getRecycleCount());
        assertTrue(dto.getAverageRepairabilityScore() > 0);
        assertFalse(dto.getCategoryDistribution().isEmpty());
        assertFalse(dto.getBrandDistribution().isEmpty());
    }

    @Test
    @DisplayName("Division by zero in completion rate returns 0.0 safely without exception")
    void testCompletionRate_DivisionByZero_Guarded() {
        // Date range with zero bookings
        RepairAnalyticsDTO dto = analyticsService.getAdminRepairAnalytics(LocalDate.now().minusYears(10), LocalDate.now().minusYears(9));
        assertNotNull(dto);
        assertEquals(0, dto.getTotalBookings());
        assertEquals(0.0, dto.getCompletionRate());
        assertEquals(0.0, dto.getAverageEstimatedCost());
    }

    @Test
    @DisplayName("Vendor Analytics returns workload and completion stats scoped to vendor")
    void testGetVendorAnalytics() {
        RepairBooking booking = new RepairBooking(
                patron, vendorProfile, testSubmission,
                LocalDate.now().plusDays(1), LocalTime.of(10, 0), "Inspect hinge"
        );
        booking.setStatus(BookingStatusType.COMPLETED);
        repairBookingRepository.save(booking);

        VendorAnalyticsDTO dto = analyticsService.getVendorAnalytics(vendorProfile.getId(), LocalDate.now().minusDays(30), LocalDate.now().plusDays(1));
        assertNotNull(dto);
        assertEquals("Atelier Analytics", dto.getBusinessName());
        assertEquals(1, dto.getAssignedRepairs());
        assertEquals(1, dto.getCompletedRepairs());
        assertEquals(100.0, dto.getRepairCompletionRate());
    }

    @Test
    @DisplayName("User Analytics returns personal metrics and balance")
    void testGetUserAnalytics() {
        UserAnalyticsDTO dto = analyticsService.getUserAnalytics(patron.getId(), LocalDate.now().minusDays(30), LocalDate.now());
        assertNotNull(dto);
        assertEquals(1, dto.getTotalSubmissions());
        assertEquals(1, dto.getRepairRecommendations());
        assertEquals(150, dto.getCurrentPointsBalance());
        assertEquals(200, dto.getLifetimePointsEarned());
        assertNotNull(dto.getPersonalActivityTimeline());
    }

    @Test
    @DisplayName("Invalid date range where from is after to throws IllegalArgumentException")
    void testInvalidDateRange_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                analyticsService.getAdminOverview(LocalDate.now().plusDays(5), LocalDate.now())
        );
    }
}

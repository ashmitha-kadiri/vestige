package com.vestige.service;

import com.vestige.dto.request.RepairBookingCreateDTO;
import com.vestige.dto.request.RepairStatusUpdateDTO;
import com.vestige.dto.response.RepairBookingResponse;
import com.vestige.exception.ForbiddenException;
import com.vestige.exception.VendorNotVerifiedException;
import com.vestige.model.*;
import com.vestige.model.enums.*;
import com.vestige.repository.*;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RepairServiceTest {

    @Autowired
    private RepairService repairService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorProfileRepository vendorProfileRepository;

    @Autowired
    private DeviceSubmissionRepository submissionRepository;

    @Autowired
    private RepairBookingRepository repairBookingRepository;

    @Autowired
    private RewardAccountRepository rewardAccountRepository;

    @Autowired
    private RewardTransactionRepository rewardTransactionRepository;

    private User patronUser;
    private User vendorUser;
    private VendorProfile verifiedVendor;
    private DeviceSubmission testSubmission;

    @BeforeEach
    void setUp() {
        patronUser = new User("Patron Alice", "alice@vestige.internal", "pw", "+919876543210", UserRole.USER);
        patronUser.setActive(true);
        patronUser.setPreferredLanguage(PreferredLanguage.en);
        patronUser = userRepository.save(patronUser);

        vendorUser = new User("Master Craftsman", "craftsman@vestige.internal", "pw", "+919888877770", UserRole.VENDOR);
        vendorUser.setActive(true);
        vendorUser.setPreferredLanguage(PreferredLanguage.en);
        vendorUser = userRepository.save(vendorUser);

        verifiedVendor = new VendorProfile();
        verifiedVendor.setUser(vendorUser);
        verifiedVendor.setBusinessName("Royal Watch & Audio Atelier");
        verifiedVendor.setBusinessType("Certified Restoration");
        verifiedVendor.setAddress("12 Heritage Way");
        verifiedVendor.setCity("Bengaluru");
        verifiedVendor.setState("Karnataka");
        verifiedVendor.setPincode("560001");
        verifiedVendor.setServiceTypes(List.of("REPAIR"));
        verifiedVendor.setDeviceCategories(List.of("SMARTPHONE", "LAPTOP"));
        verifiedVendor.setVerificationStatus(VendorVerificationStatus.VERIFIED);
        verifiedVendor = vendorProfileRepository.save(verifiedVendor);

        testSubmission = new DeviceSubmission();
        testSubmission.setUser(patronUser);
        testSubmission.setDeviceType(DeviceCategoryType.LAPTOP);
        testSubmission.setBrand("ThinkPad");
        testSubmission.setModel( "X1 Carbon");
        testSubmission.setDeviceAgeYears(3);
        testSubmission.setCondition(DeviceConditionGrade.GOOD);
        testSubmission.setEstimatedRepairCost(BigDecimal.valueOf(2500));
        testSubmission.setOriginalValue(BigDecimal.valueOf(80000));
        testSubmission.setPartAvailability(PartAvailabilityStatus.AVAILABLE);
        testSubmission.setEngineScore(85);
        testSubmission.setEngineRecommendation(EngineRecommendationType.REPAIR);
        testSubmission.setEngineConfidence(EngineConfidenceLevel.HIGH);
        testSubmission.setEngineRationale("Repair is highly feasible and economical.");
        testSubmission = submissionRepository.save(testSubmission);

        // Authenticate as patron by default
        UserPrincipal principal = UserPrincipal.fromEntity(patronUser);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities())
        );
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Patron successfully books a repair with a verified vendor")
    void testCreateBooking_Success() {
        RepairBookingCreateDTO dto = new RepairBookingCreateDTO();
        dto.setUserId(patronUser.getId());
        dto.setVendorId(verifiedVendor.getId());
        dto.setSubmissionId(testSubmission.getId());
        dto.setPreferredDate(LocalDate.now().plusDays(2));
        dto.setPreferredTime(LocalTime.of(14, 30));
        dto.setIssueDescription("Screen flickering and thermal paste renewal");

        RepairBookingResponse response = repairService.createBooking(dto);

        assertNotNull(response.getId());
        assertEquals(BookingStatusType.PENDING, response.getStatus());
        assertEquals(patronUser.getId(), response.getUserId());
        assertEquals(verifiedVendor.getId(), response.getVendorId());
        assertEquals("Screen flickering and thermal paste renewal", response.getIssueDescription());
    }

    @Test
    @DisplayName("Booking repair with an unverified vendor throws VendorNotVerifiedException")
    void testCreateBooking_UnverifiedVendor_Fails() {
        User unverifiedUser = new User("Unverified Tech", "unverified@vestige.internal", "pw", "+919888800000", UserRole.VENDOR);
        unverifiedUser = userRepository.save(unverifiedUser);

        VendorProfile pendingVendor = new VendorProfile();
        pendingVendor.setUser(unverifiedUser);
        pendingVendor.setBusinessName("Novice Repair Lab");
        pendingVendor.setBusinessType("Repair");
        pendingVendor.setAddress("1 St");
        pendingVendor.setCity("City");
        pendingVendor.setState("State");
        pendingVendor.setPincode("123456");
        pendingVendor.setServiceTypes(List.of("REPAIR"));
        pendingVendor.setDeviceCategories(List.of("SMARTPHONE"));
        pendingVendor.setVerificationStatus(VendorVerificationStatus.PENDING);
        pendingVendor = vendorProfileRepository.save(pendingVendor);

        RepairBookingCreateDTO dto = new RepairBookingCreateDTO();
        dto.setVendorId(pendingVendor.getId());
        dto.setSubmissionId(testSubmission.getId());
        dto.setPreferredDate(LocalDate.now().plusDays(1));
        dto.setIssueDescription("Battery replacement");

        assertThrows(VendorNotVerifiedException.class, () -> repairService.createBooking(dto));
    }

    @Test
    @DisplayName("Vendor transitions repair through state machine and awards 100 reward points upon completion")
    void testUpdateStatus_VendorFlow_AndRewardCredit() {
        RepairBooking booking = new RepairBooking(
                patronUser, verifiedVendor, testSubmission,
                LocalDate.now().plusDays(1), LocalTime.of(10, 0), "Diagnostic inspection"
        );
        booking = repairBookingRepository.save(booking);

        // Switch security context to assigned vendor
        UserPrincipal vendorPrincipal = UserPrincipal.fromEntity(vendorUser);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(vendorPrincipal, null, vendorPrincipal.getAuthorities())
        );

        // 1. PENDING -> ACCEPTED
        RepairStatusUpdateDTO dto1 = new RepairStatusUpdateDTO();
        dto1.setStatus(BookingStatusType.ACCEPTED);
        dto1.setNotes("Workbench scheduled for inspection");
        RepairBookingResponse res1 = repairService.updateStatus(booking.getId(), dto1);
        assertEquals(BookingStatusType.ACCEPTED, res1.getStatus());

        // 2. ACCEPTED -> IN_PROGRESS
        RepairStatusUpdateDTO dto2 = new RepairStatusUpdateDTO();
        dto2.setStatus(BookingStatusType.IN_PROGRESS);
        dto2.setNotes("Micro-soldering capacitors");
        RepairBookingResponse res2 = repairService.updateStatus(booking.getId(), dto2);
        assertEquals(BookingStatusType.IN_PROGRESS, res2.getStatus());

        // 3. IN_PROGRESS -> COMPLETED
        RepairStatusUpdateDTO dto3 = new RepairStatusUpdateDTO();
        dto3.setStatus(BookingStatusType.COMPLETED);
        dto3.setNotes("Display restored and tested");
        RepairBookingResponse res3 = repairService.updateStatus(booking.getId(), dto3);
        assertEquals(BookingStatusType.COMPLETED, res3.getStatus());

        // Verify Reward Account credited with 100 points
        RewardAccount account = rewardAccountRepository.findByUserId(patronUser.getId()).orElseThrow();
        assertEquals(100, account.getBalance());
        assertEquals(100, account.getLifetimeEarned());

        // Test idempotency: re-updating or calling completed transition does not double award points
        List<RewardTransaction> transactions = rewardTransactionRepository.findByReferenceIdAndSource(
                booking.getId(), RewardSourceType.REPAIR_COMPLETION
        );
        assertEquals(1, transactions.size());
    }

    @Test
    @DisplayName("Invalid state machine transition throws IllegalStateException")
    void testUpdateStatus_InvalidTransition_Throws() {
        final RepairBooking savedBooking = repairBookingRepository.save(new RepairBooking(
                patronUser, verifiedVendor, testSubmission,
                LocalDate.now().plusDays(1), LocalTime.of(10, 0), "Diagnostic inspection"
        ));

        UserPrincipal vendorPrincipal = UserPrincipal.fromEntity(vendorUser);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(vendorPrincipal, null, vendorPrincipal.getAuthorities())
        );

        // Attempting PENDING -> COMPLETED directly (skipping ACCEPTED/IN_PROGRESS)
        RepairStatusUpdateDTO invalidDto = new RepairStatusUpdateDTO();
        invalidDto.setStatus(BookingStatusType.COMPLETED);

        assertThrows(IllegalStateException.class, () -> repairService.updateStatus(savedBooking.getId(), invalidDto));
    }

    @Test
    @DisplayName("Non-assigned vendor attempting to update job status throws ForbiddenException")
    void testUpdateStatus_NonAssignedVendor_Throws() {
        User otherVendorUser = new User("Other Craftsman", "other@vestige.internal", "pw", "+919888899999", UserRole.VENDOR);
        otherVendorUser.setActive(true);
        otherVendorUser = userRepository.save(otherVendorUser);

        final RepairBooking savedBooking = repairBookingRepository.save(new RepairBooking(
                patronUser, verifiedVendor, testSubmission,
                LocalDate.now().plusDays(1), LocalTime.of(10, 0), "Diagnostic inspection"
        ));

        UserPrincipal otherVendorPrincipal = UserPrincipal.fromEntity(otherVendorUser);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(otherVendorPrincipal, null, otherVendorPrincipal.getAuthorities())
        );

        RepairStatusUpdateDTO dto = new RepairStatusUpdateDTO();
        dto.setStatus(BookingStatusType.ACCEPTED);

        assertThrows(ForbiddenException.class, () -> repairService.updateStatus(savedBooking.getId(), dto));
    }
}

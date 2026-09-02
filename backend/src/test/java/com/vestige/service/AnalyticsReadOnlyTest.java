package com.vestige.service;

import com.vestige.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AnalyticsReadOnlyTest {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceSubmissionRepository deviceSubmissionRepository;

    @Autowired
    private RepairBookingRepository repairBookingRepository;

    @Autowired
    private RecyclingRequestRepository recyclingRequestRepository;

    @Autowired
    private RewardTransactionRepository rewardTransactionRepository;

    @Autowired
    private AdminActionRepository adminActionRepository;

    @Test
    @DisplayName("Invoking analytics queries performs zero writes across all operational tables")
    void testAnalytics_IsStrictlyReadOnly() {
        long initialUsers = userRepository.count();
        long initialSubmissions = deviceSubmissionRepository.count();
        long initialRepairs = repairBookingRepository.count();
        long initialRecycling = recyclingRequestRepository.count();
        long initialTxs = rewardTransactionRepository.count();
        long initialAdminActions = adminActionRepository.count();

        // Execute full suite of analytics endpoints
        LocalDate from = LocalDate.now().minusDays(30);
        LocalDate to = LocalDate.now();

        analyticsService.getAdminOverview(from, to);
        analyticsService.getAdminDeviceAnalytics(from, to);
        analyticsService.getAdminRepairAnalytics(from, to);
        analyticsService.getAdminRecyclingAnalytics(from, to);
        analyticsService.getAdminRewardAnalytics(from, to);
        analyticsService.getAdminVendorWorkload(from, to);

        assertEquals(initialUsers, userRepository.count());
        assertEquals(initialSubmissions, deviceSubmissionRepository.count());
        assertEquals(initialRepairs, repairBookingRepository.count());
        assertEquals(initialRecycling, recyclingRequestRepository.count());
        assertEquals(initialTxs, rewardTransactionRepository.count());
        assertEquals(initialAdminActions, adminActionRepository.count());
    }
}

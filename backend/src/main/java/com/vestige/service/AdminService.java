package com.vestige.service;

import com.vestige.dto.admin.AdminRecyclingDetailDTO;
import com.vestige.dto.admin.AdminRepairDetailDTO;
import com.vestige.dto.admin.AdminRewardAccountDTO;
import com.vestige.dto.admin.RegistrationActivityDTO;
import com.vestige.dto.admin.performance.AdminPerformanceDTO;
import com.vestige.dto.admin.performance.AdminPerformanceDTO.*;
import com.vestige.dto.analytics.DistributionItem;
import com.vestige.dto.analytics.TimeSeriesDataPoint;
import com.vestige.dto.request.AdminUserStatusRequest;
import com.vestige.dto.request.AdminVendorVerifyRequest;
import com.vestige.dto.response.UserSummaryResponse;
import com.vestige.dto.response.VendorSummaryResponse;
import com.vestige.exception.ForbiddenException;
import com.vestige.model.AdminAction;
import com.vestige.model.User;
import com.vestige.model.VendorProfile;
import com.vestige.model.enums.*;
import com.vestige.repository.*;
import com.vestige.security.SecurityUtils;
import com.vestige.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final UserRepository userRepository;
    private final VendorProfileRepository vendorProfileRepository;
    private final AdminActionRepository adminActionRepository;
    private final RepairBookingRepository repairBookingRepository;
    private final RecyclingRequestRepository recyclingRequestRepository;
    private final RewardAccountRepository rewardAccountRepository;
    private final DeviceSubmissionRepository deviceSubmissionRepository;
    private final PaymentRepository paymentRepository;
    private final RewardTransactionRepository rewardTransactionRepository;
    private final RedemptionRepository redemptionRepository;

    public AdminService(UserRepository userRepository,
                        VendorProfileRepository vendorProfileRepository,
                        AdminActionRepository adminActionRepository,
                        RepairBookingRepository repairBookingRepository,
                        RecyclingRequestRepository recyclingRequestRepository,
                        RewardAccountRepository rewardAccountRepository,
                        DeviceSubmissionRepository deviceSubmissionRepository,
                        PaymentRepository paymentRepository,
                        RewardTransactionRepository rewardTransactionRepository,
                        RedemptionRepository redemptionRepository) {
        this.userRepository = userRepository;
        this.vendorProfileRepository = vendorProfileRepository;
        this.adminActionRepository = adminActionRepository;
        this.repairBookingRepository = repairBookingRepository;
        this.recyclingRequestRepository = recyclingRequestRepository;
        this.rewardAccountRepository = rewardAccountRepository;
        this.deviceSubmissionRepository = deviceSubmissionRepository;
        this.paymentRepository = paymentRepository;
        this.rewardTransactionRepository = rewardTransactionRepository;
        this.redemptionRepository = redemptionRepository;
    }

    @Transactional(readOnly = true)
    public List<UserSummaryResponse> getAllUsers() {
        assertAdmin();
        return userRepository.findAll().stream()
                .map(UserSummaryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserSummaryResponse updateUserStatus(UUID userId, AdminUserStatusRequest request, String ipAddress) {
        UserPrincipal admin = assertAdmin();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        user.setActive(request.getActive());
        user = userRepository.save(user);

        // Record audit action
        String details = String.format("{\"active\":%b,\"reason\":\"%s\"}",
                request.getActive(),
                request.getReason() != null ? request.getReason() : "Administrative status update");

        AdminAction action = new AdminAction(
                admin.getId(),
                request.getActive() ? "ACTIVATE_USER" : "SUSPEND_USER",
                "users",
                user.getId(),
                details,
                ipAddress
        );
        adminActionRepository.save(action);

        logger.info("Admin {} updated user {} active status to {}", admin.getId(), user.getId(), user.isActive());
        return UserSummaryResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public List<VendorSummaryResponse> getAllVendors() {
        assertAdmin();
        return vendorProfileRepository.findAll().stream()
                .map(VendorSummaryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VendorSummaryResponse> getPendingVendors() {
        assertAdmin();
        return vendorProfileRepository.findByVerificationStatus(VendorVerificationStatus.PENDING).stream()
                .map(VendorSummaryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public VendorSummaryResponse verifyVendor(UUID vendorId, AdminVendorVerifyRequest request, String ipAddress) {
        UserPrincipal admin = assertAdmin();

        VendorProfile vendor = vendorProfileRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor profile not found: " + vendorId));

        boolean isApprove = "APPROVE".equalsIgnoreCase(request.getAction());
        if (isApprove) {
            vendor.setVerificationStatus(VendorVerificationStatus.VERIFIED);
            vendor.setRejectionReason(null);
        } else {
            vendor.setVerificationStatus(VendorVerificationStatus.REJECTED);
            vendor.setRejectionReason(request.getRejectionReason() != null ? request.getRejectionReason() : "Administrative rejection");
        }

        vendor = vendorProfileRepository.save(vendor);

        AdminAction action = new AdminAction(
                admin.getId(),
                isApprove ? "APPROVE_VENDOR" : "REJECT_VENDOR",
                "vendor_profiles",
                vendor.getId(),
                String.format("{\"action\":\"%s\",\"reason\":\"%s\"}", request.getAction(), vendor.getRejectionReason()),
                ipAddress
        );
        adminActionRepository.save(action);

        logger.info("Admin {} verified vendor {}: status={}", admin.getId(), vendor.getId(), vendor.getVerificationStatus());
        return VendorSummaryResponse.fromEntity(vendor);
    }

    @Transactional(readOnly = true)
    public List<AdminRepairDetailDTO> getAllRepairs() {
        assertAdmin();
        return repairBookingRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(AdminRepairDetailDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdminRecyclingDetailDTO> getAllRecycling() {
        assertAdmin();
        return recyclingRequestRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(AdminRecyclingDetailDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdminRewardAccountDTO> getAllRewardAccounts() {
        assertAdmin();
        return rewardAccountRepository.findAll().stream()
                .map(AdminRewardAccountDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RegistrationActivityDTO getRegistrationActivity(String range) {
        assertAdmin();
        OffsetDateTime now = OffsetDateTime.now();
        List<TimeSeriesDataPoint> timeline = new ArrayList<>();
        long currentCount = 0;
        Long previousCount = null;
        Double growth = null;
        String message = "No prior data to compare";

        DateTimeFormatter dtf;
        int buckets = 6;
        boolean isDaily = "7d".equalsIgnoreCase(range) || "30d".equalsIgnoreCase(range);

        if ("7d".equalsIgnoreCase(range)) {
            dtf = DateTimeFormatter.ofPattern("EEE dd");
            buckets = 7;
            for (int i = buckets - 1; i >= 0; i--) {
                OffsetDateTime start = now.minusDays(i).withHour(0).withMinute(0).withSecond(0).withNano(0);
                OffsetDateTime end = start.plusDays(1).minusNanos(1);
                long c = userRepository.countByRoleAndDateRange(UserRole.USER, start, end);
                timeline.add(new TimeSeriesDataPoint(start.format(dtf), c));
                currentCount += c;
            }
        } else if ("30d".equalsIgnoreCase(range)) {
            dtf = DateTimeFormatter.ofPattern("MMM dd");
            buckets = 30;
            for (int i = buckets - 1; i >= 0; i -= 3) {
                OffsetDateTime start = now.minusDays(i).withHour(0).withMinute(0).withSecond(0).withNano(0);
                OffsetDateTime end = start.plusDays(3).minusNanos(1);
                long c = userRepository.countByRoleAndDateRange(UserRole.USER, start, end);
                timeline.add(new TimeSeriesDataPoint(start.format(dtf), c));
                currentCount += c;
            }
        } else {
            // Default 6m / 12m
            dtf = DateTimeFormatter.ofPattern("MMM yyyy");
            buckets = "12m".equalsIgnoreCase(range) ? 12 : 6;
            for (int i = buckets - 1; i >= 0; i--) {
                OffsetDateTime start = now.minusMonths(i).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
                OffsetDateTime end = start.plusMonths(1).minusNanos(1);
                long c = userRepository.countByRoleAndDateRange(UserRole.USER, start, end);
                timeline.add(new TimeSeriesDataPoint(start.format(dtf), c));
                currentCount += c;
            }
        }

        // Compare with previous equivalent period if historical data exists
        if (timeline.size() >= 2) {
            long firstHalf = timeline.stream().limit(timeline.size() / 2).mapToLong(TimeSeriesDataPoint::getCount).sum();
            long secondHalf = timeline.stream().skip(timeline.size() / 2).mapToLong(TimeSeriesDataPoint::getCount).sum();
            if (firstHalf > 0) {
                previousCount = firstHalf;
                growth = Math.round(((double) (secondHalf - firstHalf) / firstHalf * 100.0) * 10.0) / 10.0;
                message = growth >= 0 ? "+" + growth + "% period-over-period" : growth + "% period-over-period";
            }
        }

        return new RegistrationActivityDTO(
                currentCount,
                previousCount,
                growth,
                message,
                timeline
        );
    }

    @Transactional(readOnly = true)
    public AdminPerformanceDTO getAdminPerformance(String range) {
        assertAdmin();
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        String selectedRange = (range != null && !range.isBlank()) ? range.toLowerCase() : "30d";

        OffsetDateTime rangeStart;
        DateTimeFormatter dtf;

        switch (selectedRange) {
            case "7d":
                rangeStart = now.minusDays(7);
                dtf = DateTimeFormatter.ofPattern("EEE dd");
                break;
            case "90d":
                rangeStart = now.minusDays(90);
                dtf = DateTimeFormatter.ofPattern("MMM dd");
                break;
            case "12m":
                rangeStart = now.minusMonths(12);
                dtf = DateTimeFormatter.ofPattern("MMM yyyy");
                break;
            case "30d":
            default:
                selectedRange = "30d";
                rangeStart = now.minusDays(30);
                dtf = DateTimeFormatter.ofPattern("MMM dd");
                break;
        }

        AdminPerformanceDTO performance = new AdminPerformanceDTO();

        // 1. Business KPIs
        BusinessKpiSummary kpi = new BusinessKpiSummary();
        kpi.setTotalUsers(userRepository.countByRole(UserRole.USER));
        kpi.setActiveUsers(userRepository.countByIsActiveTrue());

        OffsetDateTime startOfToday = now.toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime endOfToday = now;
        kpi.setRegistrationsToday(userRepository.countByRoleAndDateRange(UserRole.USER, startOfToday, endOfToday));

        OffsetDateTime startOfWeek = now.minusDays(7);
        kpi.setRegistrationsThisWeek(userRepository.countByRoleAndDateRange(UserRole.USER, startOfWeek, endOfToday));

        OffsetDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
        long currentMonthRegs = userRepository.countByRoleAndDateRange(UserRole.USER, startOfMonth, endOfToday);
        kpi.setRegistrationsThisMonth(currentMonthRegs);

        OffsetDateTime startOfPrevMonth = now.minusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime endOfPrevMonth = startOfMonth.minusNanos(1);
        long prevMonthRegs = userRepository.countByRoleAndDateRange(UserRole.USER, startOfPrevMonth, endOfPrevMonth);
        kpi.setPreviousMonthRegistrations(prevMonthRegs);

        if (prevMonthRegs > 0) {
            double uGrowth = Math.round(((double) (currentMonthRegs - prevMonthRegs) / prevMonthRegs * 100.0) * 10.0) / 10.0;
            kpi.setUserGrowthRate(uGrowth);
        }

        long totalVendors = vendorProfileRepository.count();
        long verifiedVendors = vendorProfileRepository.countByVerificationStatus(VendorVerificationStatus.VERIFIED);
        long pendingVendors = vendorProfileRepository.countByVerificationStatus(VendorVerificationStatus.PENDING);
        kpi.setTotalVendors(totalVendors);
        kpi.setVerifiedVendors(verifiedVendors);
        kpi.setPendingVendors(pendingVendors);

        long prevVendors = userRepository.countByRoleAndDateRange(UserRole.VENDOR, startOfPrevMonth, endOfPrevMonth);
        long curVendors = userRepository.countByRoleAndDateRange(UserRole.VENDOR, startOfMonth, endOfToday);
        if (prevVendors > 0) {
            double vGrowth = Math.round(((double) (curVendors - prevVendors) / prevVendors * 100.0) * 10.0) / 10.0;
            kpi.setVendorGrowthRate(vGrowth);
        }

        kpi.setDevicesAssessed(deviceSubmissionRepository.countByCreatedAtBetween(rangeStart, now));
        kpi.setRepairRecommendations(deviceSubmissionRepository.countByEngineRecommendationAndCreatedAtBetween(EngineRecommendationType.REPAIR, rangeStart, now));
        kpi.setRecycleRecommendations(deviceSubmissionRepository.countByEngineRecommendationAndCreatedAtBetween(EngineRecommendationType.RECYCLE, rangeStart, now));

        long totalRepairs = repairBookingRepository.countByCreatedAtBetween(rangeStart, now);
        long completedRepairs = repairBookingRepository.countByStatusAndCreatedAtBetween(BookingStatusType.COMPLETED, rangeStart, now);
        long pendingRepairs = repairBookingRepository.countByStatusAndCreatedAtBetween(BookingStatusType.PENDING, rangeStart, now);
        kpi.setTotalRepairs(totalRepairs);
        kpi.setCompletedRepairs(completedRepairs);
        long eligibleRepairs = totalRepairs - pendingRepairs;
        kpi.setRepairCompletionRate(eligibleRepairs > 0 ? Math.round(((double) completedRepairs / eligibleRepairs * 100.0) * 10.0) / 10.0 : 0.0);

        long totalRecycling = recyclingRequestRepository.countByCreatedAtBetween(rangeStart, now);
        long completedRecycling = recyclingRequestRepository.countByStatusAndCreatedAtBetween(RecyclingStatusType.COMPLETED, rangeStart, now);
        long pendingRecycling = recyclingRequestRepository.countByStatusAndCreatedAtBetween(RecyclingStatusType.PENDING, rangeStart, now);
        kpi.setTotalRecycling(totalRecycling);
        kpi.setCompletedRecycling(completedRecycling);
        long eligibleRecycling = totalRecycling - pendingRecycling;
        kpi.setRecyclingCompletionRate(eligibleRecycling > 0 ? Math.round(((double) completedRecycling / eligibleRecycling * 100.0) * 10.0) / 10.0 : 0.0);

        kpi.setRewardsEarned(rewardTransactionRepository.sumPointsByTypeAndCreatedAtBetween(RewardTransactionType.EARNED, rangeStart, now));
        kpi.setRewardsRedeemed(rewardTransactionRepository.sumPointsByTypeAndCreatedAtBetween(RewardTransactionType.REDEEMED, rangeStart, now));
        kpi.setOutstandingRewardBalance(rewardAccountRepository.calculateTotalOutstandingBalance());

        kpi.setSuccessfulPayments(paymentRepository.countByStatusAndDateRange(PaymentStatusType.SUCCESS, rangeStart, now));
        BigDecimal vol = paymentRepository.sumSuccessfulRevenueByDateRange(rangeStart, now);
        kpi.setPaymentVolumeInr(vol != null ? vol : BigDecimal.ZERO);
        kpi.setFailedPayments(paymentRepository.countByStatusAndDateRange(PaymentStatusType.FAILED, rangeStart, now));

        performance.setBusinessKpis(kpi);

        // 2. Growth Comparison Timelines (Users & Vendors)
        GrowthComparison growth = new GrowthComparison();
        growth.setRange(selectedRange);
        List<TimeSeriesDataPoint> userPts = new ArrayList<>();
        List<TimeSeriesDataPoint> vendorPts = new ArrayList<>();
        long periodUsers = 0;
        long periodVendors = 0;

        if ("7d".equals(selectedRange)) {
            for (int i = 6; i >= 0; i--) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(1).minusNanos(1);
                long uCount = userRepository.countByRoleAndDateRange(UserRole.USER, s, e);
                long vCount = userRepository.countByRoleAndDateRange(UserRole.VENDOR, s, e);
                String label = s.format(dtf);
                userPts.add(new TimeSeriesDataPoint(label, uCount));
                vendorPts.add(new TimeSeriesDataPoint(label, vCount));
                periodUsers += uCount;
                periodVendors += vCount;
            }
        } else if ("30d".equals(selectedRange)) {
            for (int i = 27; i >= 0; i -= 3) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(3).minusNanos(1);
                long uCount = userRepository.countByRoleAndDateRange(UserRole.USER, s, e);
                long vCount = userRepository.countByRoleAndDateRange(UserRole.VENDOR, s, e);
                String label = s.format(dtf);
                userPts.add(new TimeSeriesDataPoint(label, uCount));
                vendorPts.add(new TimeSeriesDataPoint(label, vCount));
                periodUsers += uCount;
                periodVendors += vCount;
            }
        } else if ("90d".equals(selectedRange)) {
            for (int i = 84; i >= 0; i -= 7) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(7).minusNanos(1);
                long uCount = userRepository.countByRoleAndDateRange(UserRole.USER, s, e);
                long vCount = userRepository.countByRoleAndDateRange(UserRole.VENDOR, s, e);
                String label = s.format(dtf);
                userPts.add(new TimeSeriesDataPoint(label, uCount));
                vendorPts.add(new TimeSeriesDataPoint(label, vCount));
                periodUsers += uCount;
                periodVendors += vCount;
            }
        } else {
            // 12m
            for (int i = 11; i >= 0; i--) {
                OffsetDateTime s = now.minusMonths(i).withDayOfMonth(1).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusMonths(1).minusNanos(1);
                long uCount = userRepository.countByRoleAndDateRange(UserRole.USER, s, e);
                long vCount = userRepository.countByRoleAndDateRange(UserRole.VENDOR, s, e);
                String label = s.format(dtf);
                userPts.add(new TimeSeriesDataPoint(label, uCount));
                vendorPts.add(new TimeSeriesDataPoint(label, vCount));
                periodUsers += uCount;
                periodVendors += vCount;
            }
        }
        growth.setUserTimeline(userPts);
        growth.setVendorTimeline(vendorPts);
        growth.setTotalUsersInPeriod(periodUsers);
        growth.setTotalVendorsInPeriod(periodVendors);
        performance.setGrowthComparison(growth);

        // 3. Repair Performance
        RepairPerformance rep = new RepairPerformance();
        rep.setTotalRequests(totalRepairs);
        long repAccepted = repairBookingRepository.countByStatusAndCreatedAtBetween(BookingStatusType.ACCEPTED, rangeStart, now);
        long repInProgress = repairBookingRepository.countByStatusAndCreatedAtBetween(BookingStatusType.IN_PROGRESS, rangeStart, now);
        long repCancelled = repairBookingRepository.countByStatusAndCreatedAtBetween(BookingStatusType.CANCELLED, rangeStart, now);
        long repRejected = repairBookingRepository.countByStatusAndCreatedAtBetween(BookingStatusType.REJECTED, rangeStart, now);
        rep.setPendingCount(pendingRepairs);
        rep.setAcceptedCount(repAccepted);
        rep.setInProgressCount(repInProgress);
        rep.setCompletedCount(completedRepairs);
        rep.setCancelledCount(repCancelled);
        rep.setRejectedCount(repRejected);
        rep.setCompletionRate(kpi.getRepairCompletionRate());
        rep.setCancellationRate(totalRepairs > 0 ? Math.round(((double) repCancelled / totalRepairs * 100.0) * 10.0) / 10.0 : 0.0);
        rep.setAverageCompletionTime("Data unavailable: Historical completion duration timestamps not recorded in current schema.");

        List<DistributionItem> repDist = new ArrayList<>();
        repDist.add(new DistributionItem("COMPLETED", "Completed", completedRepairs, totalRepairs > 0 ? Math.round(((double) completedRepairs / totalRepairs) * 1000.0) / 10.0 : 0.0));
        repDist.add(new DistributionItem("IN_PROGRESS", "In Progress", repInProgress, totalRepairs > 0 ? Math.round(((double) repInProgress / totalRepairs) * 1000.0) / 10.0 : 0.0));
        repDist.add(new DistributionItem("ACCEPTED", "Accepted", repAccepted, totalRepairs > 0 ? Math.round(((double) repAccepted / totalRepairs) * 1000.0) / 10.0 : 0.0));
        repDist.add(new DistributionItem("PENDING", "Pending", pendingRepairs, totalRepairs > 0 ? Math.round(((double) pendingRepairs / totalRepairs) * 1000.0) / 10.0 : 0.0));
        repDist.add(new DistributionItem("CANCELLED", "Cancelled", repCancelled, totalRepairs > 0 ? Math.round(((double) repCancelled / totalRepairs) * 1000.0) / 10.0 : 0.0));
        repDist.add(new DistributionItem("REJECTED", "Rejected", repRejected, totalRepairs > 0 ? Math.round(((double) repRejected / totalRepairs) * 1000.0) / 10.0 : 0.0));
        rep.setStatusDistribution(repDist);
        rep.setTimeline(buildRepairTimeline(selectedRange, now, dtf));
        performance.setRepairPerformance(rep);

        // 4. Recycling Performance
        RecyclingPerformance rec = new RecyclingPerformance();
        rec.setTotalRequests(totalRecycling);
        long recAccepted = recyclingRequestRepository.countByStatusAndCreatedAtBetween(RecyclingStatusType.ACCEPTED, rangeStart, now);
        long recScheduled = recyclingRequestRepository.countByStatusAndCreatedAtBetween(RecyclingStatusType.SCHEDULED, rangeStart, now);
        long recCancelled = recyclingRequestRepository.countByStatusAndCreatedAtBetween(RecyclingStatusType.CANCELLED, rangeStart, now);
        rec.setPendingCount(pendingRecycling);
        rec.setAcceptedCount(recAccepted);
        rec.setScheduledCount(recScheduled);
        rec.setCompletedCount(completedRecycling);
        rec.setCancelledCount(recCancelled);
        rec.setCompletionRate(kpi.getRecyclingCompletionRate());
        rec.setCancellationRate(totalRecycling > 0 ? Math.round(((double) recCancelled / totalRecycling * 100.0) * 10.0) / 10.0 : 0.0);
        rec.setAverageCompletionTime("Data unavailable: Historical collection duration timestamps not recorded in current schema.");

        List<DistributionItem> recDist = new ArrayList<>();
        recDist.add(new DistributionItem("COMPLETED", "Completed", completedRecycling, totalRecycling > 0 ? Math.round(((double) completedRecycling / totalRecycling) * 1000.0) / 10.0 : 0.0));
        recDist.add(new DistributionItem("SCHEDULED", "Scheduled", recScheduled, totalRecycling > 0 ? Math.round(((double) recScheduled / totalRecycling) * 1000.0) / 10.0 : 0.0));
        recDist.add(new DistributionItem("ACCEPTED", "Accepted", recAccepted, totalRecycling > 0 ? Math.round(((double) recAccepted / totalRecycling) * 1000.0) / 10.0 : 0.0));
        recDist.add(new DistributionItem("PENDING", "Pending", pendingRecycling, totalRecycling > 0 ? Math.round(((double) pendingRecycling / totalRecycling) * 1000.0) / 10.0 : 0.0));
        recDist.add(new DistributionItem("CANCELLED", "Cancelled", recCancelled, totalRecycling > 0 ? Math.round(((double) recCancelled / totalRecycling) * 1000.0) / 10.0 : 0.0));
        rec.setStatusDistribution(recDist);
        rec.setTimeline(buildRecyclingTimeline(selectedRange, now, dtf));
        performance.setRecyclingPerformance(rec);

        // 5. Reward Performance
        RewardPerformance rew = new RewardPerformance();
        rew.setPointsEarned(kpi.getRewardsEarned());
        rew.setPointsRedeemed(kpi.getRewardsRedeemed());
        rew.setOutstandingBalance(kpi.getOutstandingRewardBalance());
        rew.setTotalTransactions(rewardTransactionRepository.countByCreatedAtBetween(rangeStart, now));
        rew.setTotalRedemptions(redemptionRepository.countByCreatedAtBetween(rangeStart, now));
        List<Object[]> sources = rewardTransactionRepository.sumEarnedPointsGroupedBySource(rangeStart, now);
        List<DistributionItem> srcDist = new ArrayList<>();
        long totIssued = rew.getPointsEarned();
        for (Object[] r : sources) {
            String sk = r[0].toString();
            long pts = ((Number) r[1]).longValue();
            double pct = totIssued > 0 ? ((double) pts / totIssued) * 100.0 : 0.0;
            srcDist.add(new DistributionItem(sk, sk.replace('_', ' '), pts, Math.round(pct * 10.0) / 10.0));
        }
        rew.setPointsBySource(srcDist);
        rew.setTimeline(buildRewardTimeline(selectedRange, now, dtf));
        performance.setRewardPerformance(rew);

        // 6. Payment Performance
        PaymentPerformance pay = new PaymentPerformance();
        pay.setTotalTransactions(paymentRepository.countByDateRange(rangeStart, now));
        pay.setSuccessfulCount(kpi.getSuccessfulPayments());
        pay.setFailedCount(kpi.getFailedPayments());
        pay.setPendingCount(paymentRepository.countByStatusAndDateRange(PaymentStatusType.PENDING, rangeStart, now));
        pay.setTotalVolumeInr(kpi.getPaymentVolumeInr());
        pay.setTimeline(buildPaymentTimeline(selectedRange, now, dtf));
        performance.setPaymentPerformance(pay);

        // 7. System Performance (Honest Real Metrics & DB Latency Ping)
        SystemPerformance sys = new SystemPerformance();
        long pingStart = System.currentTimeMillis();
        userRepository.count(); // Real database roundtrip ping
        long dbLatency = System.currentTimeMillis() - pingStart;

        Runtime runtime = Runtime.getRuntime();
        long usedMb = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        long maxMb = runtime.maxMemory() / (1024 * 1024);
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;

        sys.setBackendStatus("HEALTHY");
        sys.setDatabaseStatus("CONNECTED");
        sys.setDatabaseLatencyMs(dbLatency);
        sys.setJvmUptimeSeconds(uptime);
        sys.setJvmUsedMemoryMb(usedMb);
        sys.setJvmMaxMemoryMb(maxMb);
        sys.setTelemetryAvailable(false);
        sys.setTelemetryNotice("System telemetry infrastructure (APM / Micrometer / Actuator metrics) is not currently enabled. Endpoint latency percentiles, request volume histograms, and HTTP 4xx/5xx error rates require dedicated APM instrumentation.");
        sys.setRequiredInfrastructure("Spring Boot Actuator + Micrometer APM Registry");
        performance.setSystemPerformance(sys);

        // 8. Recent Activity Ledger
        performance.setRecentActivity(adminActionRepository.findAllByOrderByCreatedAtDesc().stream().limit(10).collect(Collectors.toList()));

        return performance;
    }

    private List<TimeSeriesDataPoint> buildRepairTimeline(String range, OffsetDateTime now, DateTimeFormatter dtf) {
        List<TimeSeriesDataPoint> points = new ArrayList<>();
        if ("7d".equals(range)) {
            for (int i = 6; i >= 0; i--) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(1).minusNanos(1);
                long c = repairBookingRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else if ("30d".equals(range)) {
            for (int i = 27; i >= 0; i -= 3) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(3).minusNanos(1);
                long c = repairBookingRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else if ("90d".equals(range)) {
            for (int i = 84; i >= 0; i -= 7) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(7).minusNanos(1);
                long c = repairBookingRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else {
            for (int i = 11; i >= 0; i--) {
                OffsetDateTime s = now.minusMonths(i).withDayOfMonth(1).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusMonths(1).minusNanos(1);
                long c = repairBookingRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        }
        return points;
    }

    private List<TimeSeriesDataPoint> buildRecyclingTimeline(String range, OffsetDateTime now, DateTimeFormatter dtf) {
        List<TimeSeriesDataPoint> points = new ArrayList<>();
        if ("7d".equals(range)) {
            for (int i = 6; i >= 0; i--) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(1).minusNanos(1);
                long c = recyclingRequestRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else if ("30d".equals(range)) {
            for (int i = 27; i >= 0; i -= 3) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(3).minusNanos(1);
                long c = recyclingRequestRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else if ("90d".equals(range)) {
            for (int i = 84; i >= 0; i -= 7) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(7).minusNanos(1);
                long c = recyclingRequestRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else {
            for (int i = 11; i >= 0; i--) {
                OffsetDateTime s = now.minusMonths(i).withDayOfMonth(1).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusMonths(1).minusNanos(1);
                long c = recyclingRequestRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        }
        return points;
    }

    private List<TimeSeriesDataPoint> buildRewardTimeline(String range, OffsetDateTime now, DateTimeFormatter dtf) {
        List<TimeSeriesDataPoint> points = new ArrayList<>();
        if ("7d".equals(range)) {
            for (int i = 6; i >= 0; i--) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(1).minusNanos(1);
                long c = rewardTransactionRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else if ("30d".equals(range)) {
            for (int i = 27; i >= 0; i -= 3) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(3).minusNanos(1);
                long c = rewardTransactionRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else if ("90d".equals(range)) {
            for (int i = 84; i >= 0; i -= 7) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(7).minusNanos(1);
                long c = rewardTransactionRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else {
            for (int i = 11; i >= 0; i--) {
                OffsetDateTime s = now.minusMonths(i).withDayOfMonth(1).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusMonths(1).minusNanos(1);
                long c = rewardTransactionRepository.countByCreatedAtBetween(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        }
        return points;
    }

    private List<TimeSeriesDataPoint> buildPaymentTimeline(String range, OffsetDateTime now, DateTimeFormatter dtf) {
        List<TimeSeriesDataPoint> points = new ArrayList<>();
        if ("7d".equals(range)) {
            for (int i = 6; i >= 0; i--) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(1).minusNanos(1);
                long c = paymentRepository.countByDateRange(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else if ("30d".equals(range)) {
            for (int i = 27; i >= 0; i -= 3) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(3).minusNanos(1);
                long c = paymentRepository.countByDateRange(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else if ("90d".equals(range)) {
            for (int i = 84; i >= 0; i -= 7) {
                OffsetDateTime s = now.minusDays(i).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusDays(7).minusNanos(1);
                long c = paymentRepository.countByDateRange(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        } else {
            for (int i = 11; i >= 0; i--) {
                OffsetDateTime s = now.minusMonths(i).withDayOfMonth(1).toLocalDate().atStartOfDay().atOffset(ZoneOffset.UTC);
                OffsetDateTime e = s.plusMonths(1).minusNanos(1);
                long c = paymentRepository.countByDateRange(s, e);
                points.add(new TimeSeriesDataPoint(s.format(dtf), c));
            }
        }
        return points;
    }

    @Transactional(readOnly = true)
    public List<AdminAction> getAdminActions() {
        assertAdmin();
        return adminActionRepository.findAllByOrderByCreatedAtDesc();
    }

    private UserPrincipal assertAdmin() {
        UserPrincipal principal = SecurityUtils.requireCurrentUser();
        SecurityUtils.assertActive();
        if (principal.getRole() != UserRole.ADMIN) {
            throw new ForbiddenException("Administrative privileges required for this operation");
        }
        return principal;
    }
}

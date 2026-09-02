package com.vestige.dto.admin.performance;

import com.vestige.dto.analytics.DistributionItem;
import com.vestige.dto.analytics.TimeSeriesDataPoint;
import com.vestige.model.AdminAction;

import java.math.BigDecimal;
import java.util.List;

public class AdminPerformanceDTO {

    private BusinessKpiSummary businessKpis;
    private GrowthComparison growthComparison;
    private RepairPerformance repairPerformance;
    private RecyclingPerformance recyclingPerformance;
    private RewardPerformance rewardPerformance;
    private PaymentPerformance paymentPerformance;
    private SystemPerformance systemPerformance;
    private List<AdminAction> recentActivity;

    public AdminPerformanceDTO() {
    }

    public BusinessKpiSummary getBusinessKpis() {
        return businessKpis;
    }

    public void setBusinessKpis(BusinessKpiSummary businessKpis) {
        this.businessKpis = businessKpis;
    }

    public GrowthComparison getGrowthComparison() {
        return growthComparison;
    }

    public void setGrowthComparison(GrowthComparison growthComparison) {
        this.growthComparison = growthComparison;
    }

    public RepairPerformance getRepairPerformance() {
        return repairPerformance;
    }

    public void setRepairPerformance(RepairPerformance repairPerformance) {
        this.repairPerformance = repairPerformance;
    }

    public RecyclingPerformance getRecyclingPerformance() {
        return recyclingPerformance;
    }

    public void setRecyclingPerformance(RecyclingPerformance recyclingPerformance) {
        this.recyclingPerformance = recyclingPerformance;
    }

    public RewardPerformance getRewardPerformance() {
        return rewardPerformance;
    }

    public void setRewardPerformance(RewardPerformance rewardPerformance) {
        this.rewardPerformance = rewardPerformance;
    }

    public PaymentPerformance getPaymentPerformance() {
        return paymentPerformance;
    }

    public void setPaymentPerformance(PaymentPerformance paymentPerformance) {
        this.paymentPerformance = paymentPerformance;
    }

    public SystemPerformance getSystemPerformance() {
        return systemPerformance;
    }

    public void setSystemPerformance(SystemPerformance systemPerformance) {
        this.systemPerformance = systemPerformance;
    }

    public List<AdminAction> getRecentActivity() {
        return recentActivity;
    }

    public void setRecentActivity(List<AdminAction> recentActivity) {
        this.recentActivity = recentActivity;
    }

    // --------------------------------------------------------------------------
    // Inner Data Structures
    // --------------------------------------------------------------------------

    public static class BusinessKpiSummary {
        private long totalUsers;
        private long activeUsers;
        private long registrationsToday;
        private long registrationsThisWeek;
        private long registrationsThisMonth;
        private Long previousMonthRegistrations;
        private Double userGrowthRate;

        private long totalVendors;
        private long verifiedVendors;
        private long pendingVendors;
        private Double vendorGrowthRate;

        private long devicesAssessed;
        private long repairRecommendations;
        private long recycleRecommendations;

        private long totalRepairs;
        private long completedRepairs;
        private Double repairCompletionRate;

        private long totalRecycling;
        private long completedRecycling;
        private Double recyclingCompletionRate;

        private long rewardsEarned;
        private long rewardsRedeemed;
        private long outstandingRewardBalance;

        private long successfulPayments;
        private BigDecimal paymentVolumeInr;
        private long failedPayments;

        public BusinessKpiSummary() {}

        public long getTotalUsers() { return totalUsers; }
        public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
        public long getActiveUsers() { return activeUsers; }
        public void setActiveUsers(long activeUsers) { this.activeUsers = activeUsers; }
        public long getRegistrationsToday() { return registrationsToday; }
        public void setRegistrationsToday(long registrationsToday) { this.registrationsToday = registrationsToday; }
        public long getRegistrationsThisWeek() { return registrationsThisWeek; }
        public void setRegistrationsThisWeek(long registrationsThisWeek) { this.registrationsThisWeek = registrationsThisWeek; }
        public long getRegistrationsThisMonth() { return registrationsThisMonth; }
        public void setRegistrationsThisMonth(long registrationsThisMonth) { this.registrationsThisMonth = registrationsThisMonth; }
        public Long getPreviousMonthRegistrations() { return previousMonthRegistrations; }
        public void setPreviousMonthRegistrations(Long previousMonthRegistrations) { this.previousMonthRegistrations = previousMonthRegistrations; }
        public Double getUserGrowthRate() { return userGrowthRate; }
        public void setUserGrowthRate(Double userGrowthRate) { this.userGrowthRate = userGrowthRate; }
        public long getTotalVendors() { return totalVendors; }
        public void setTotalVendors(long totalVendors) { this.totalVendors = totalVendors; }
        public long getVerifiedVendors() { return verifiedVendors; }
        public void setVerifiedVendors(long verifiedVendors) { this.verifiedVendors = verifiedVendors; }
        public long getPendingVendors() { return pendingVendors; }
        public void setPendingVendors(long pendingVendors) { this.pendingVendors = pendingVendors; }
        public Double getVendorGrowthRate() { return vendorGrowthRate; }
        public void setVendorGrowthRate(Double vendorGrowthRate) { this.vendorGrowthRate = vendorGrowthRate; }
        public long getDevicesAssessed() { return devicesAssessed; }
        public void setDevicesAssessed(long devicesAssessed) { this.devicesAssessed = devicesAssessed; }
        public long getRepairRecommendations() { return repairRecommendations; }
        public void setRepairRecommendations(long repairRecommendations) { this.repairRecommendations = repairRecommendations; }
        public long getRecycleRecommendations() { return recycleRecommendations; }
        public void setRecycleRecommendations(long recycleRecommendations) { this.recycleRecommendations = recycleRecommendations; }
        public long getTotalRepairs() { return totalRepairs; }
        public void setTotalRepairs(long totalRepairs) { this.totalRepairs = totalRepairs; }
        public long getCompletedRepairs() { return completedRepairs; }
        public void setCompletedRepairs(long completedRepairs) { this.completedRepairs = completedRepairs; }
        public Double getRepairCompletionRate() { return repairCompletionRate; }
        public void setRepairCompletionRate(Double repairCompletionRate) { this.repairCompletionRate = repairCompletionRate; }
        public long getTotalRecycling() { return totalRecycling; }
        public void setTotalRecycling(long totalRecycling) { this.totalRecycling = totalRecycling; }
        public long getCompletedRecycling() { return completedRecycling; }
        public void setCompletedRecycling(long completedRecycling) { this.completedRecycling = completedRecycling; }
        public Double getRecyclingCompletionRate() { return recyclingCompletionRate; }
        public void setRecyclingCompletionRate(Double recyclingCompletionRate) { this.recyclingCompletionRate = recyclingCompletionRate; }
        public long getRewardsEarned() { return rewardsEarned; }
        public void setRewardsEarned(long rewardsEarned) { this.rewardsEarned = rewardsEarned; }
        public long getRewardsRedeemed() { return rewardsRedeemed; }
        public void setRewardsRedeemed(long rewardsRedeemed) { this.rewardsRedeemed = rewardsRedeemed; }
        public long getOutstandingRewardBalance() { return outstandingRewardBalance; }
        public void setOutstandingRewardBalance(long outstandingRewardBalance) { this.outstandingRewardBalance = outstandingRewardBalance; }
        public long getSuccessfulPayments() { return successfulPayments; }
        public void setSuccessfulPayments(long successfulPayments) { this.successfulPayments = successfulPayments; }
        public BigDecimal getPaymentVolumeInr() { return paymentVolumeInr; }
        public void setPaymentVolumeInr(BigDecimal paymentVolumeInr) { this.paymentVolumeInr = paymentVolumeInr; }
        public long getFailedPayments() { return failedPayments; }
        public void setFailedPayments(long failedPayments) { this.failedPayments = failedPayments; }
    }

    public static class GrowthComparison {
        private String range;
        private List<TimeSeriesDataPoint> userTimeline;
        private List<TimeSeriesDataPoint> vendorTimeline;
        private long totalUsersInPeriod;
        private long totalVendorsInPeriod;

        public GrowthComparison() {}

        public String getRange() { return range; }
        public void setRange(String range) { this.range = range; }
        public List<TimeSeriesDataPoint> getUserTimeline() { return userTimeline; }
        public void setUserTimeline(List<TimeSeriesDataPoint> userTimeline) { this.userTimeline = userTimeline; }
        public List<TimeSeriesDataPoint> getVendorTimeline() { return vendorTimeline; }
        public void setVendorTimeline(List<TimeSeriesDataPoint> vendorTimeline) { this.vendorTimeline = vendorTimeline; }
        public long getTotalUsersInPeriod() { return totalUsersInPeriod; }
        public void setTotalUsersInPeriod(long totalUsersInPeriod) { this.totalUsersInPeriod = totalUsersInPeriod; }
        public long getTotalVendorsInPeriod() { return totalVendorsInPeriod; }
        public void setTotalVendorsInPeriod(long totalVendorsInPeriod) { this.totalVendorsInPeriod = totalVendorsInPeriod; }
    }

    public static class RepairPerformance {
        private long totalRequests;
        private long pendingCount;
        private long acceptedCount;
        private long inProgressCount;
        private long completedCount;
        private long cancelledCount;
        private long rejectedCount;
        private Double completionRate;
        private Double cancellationRate;
        private String averageCompletionTime;
        private List<DistributionItem> statusDistribution;
        private List<TimeSeriesDataPoint> timeline;

        public RepairPerformance() {}

        public long getTotalRequests() { return totalRequests; }
        public void setTotalRequests(long totalRequests) { this.totalRequests = totalRequests; }
        public long getPendingCount() { return pendingCount; }
        public void setPendingCount(long pendingCount) { this.pendingCount = pendingCount; }
        public long getAcceptedCount() { return acceptedCount; }
        public void setAcceptedCount(long acceptedCount) { this.acceptedCount = acceptedCount; }
        public long getInProgressCount() { return inProgressCount; }
        public void setInProgressCount(long inProgressCount) { this.inProgressCount = inProgressCount; }
        public long getCompletedCount() { return completedCount; }
        public void setCompletedCount(long completedCount) { this.completedCount = completedCount; }
        public long getCancelledCount() { return cancelledCount; }
        public void setCancelledCount(long cancelledCount) { this.cancelledCount = cancelledCount; }
        public long getRejectedCount() { return rejectedCount; }
        public void setRejectedCount(long rejectedCount) { this.rejectedCount = rejectedCount; }
        public Double getCompletionRate() { return completionRate; }
        public void setCompletionRate(Double completionRate) { this.completionRate = completionRate; }
        public Double getCancellationRate() { return cancellationRate; }
        public void setCancellationRate(Double cancellationRate) { this.cancellationRate = cancellationRate; }
        public String getAverageCompletionTime() { return averageCompletionTime; }
        public void setAverageCompletionTime(String averageCompletionTime) { this.averageCompletionTime = averageCompletionTime; }
        public List<DistributionItem> getStatusDistribution() { return statusDistribution; }
        public void setStatusDistribution(List<DistributionItem> statusDistribution) { this.statusDistribution = statusDistribution; }
        public List<TimeSeriesDataPoint> getTimeline() { return timeline; }
        public void setTimeline(List<TimeSeriesDataPoint> timeline) { this.timeline = timeline; }
    }

    public static class RecyclingPerformance {
        private long totalRequests;
        private long pendingCount;
        private long acceptedCount;
        private long scheduledCount;
        private long completedCount;
        private long cancelledCount;
        private Double completionRate;
        private Double cancellationRate;
        private String averageCompletionTime;
        private List<DistributionItem> statusDistribution;
        private List<TimeSeriesDataPoint> timeline;

        public RecyclingPerformance() {}

        public long getTotalRequests() { return totalRequests; }
        public void setTotalRequests(long totalRequests) { this.totalRequests = totalRequests; }
        public long getPendingCount() { return pendingCount; }
        public void setPendingCount(long pendingCount) { this.pendingCount = pendingCount; }
        public long getAcceptedCount() { return acceptedCount; }
        public void setAcceptedCount(long acceptedCount) { this.acceptedCount = acceptedCount; }
        public long getScheduledCount() { return scheduledCount; }
        public void setScheduledCount(long scheduledCount) { this.scheduledCount = scheduledCount; }
        public long getCompletedCount() { return completedCount; }
        public void setCompletedCount(long completedCount) { this.completedCount = completedCount; }
        public long getCancelledCount() { return cancelledCount; }
        public void setCancelledCount(long cancelledCount) { this.cancelledCount = cancelledCount; }
        public Double getCompletionRate() { return completionRate; }
        public void setCompletionRate(Double completionRate) { this.completionRate = completionRate; }
        public Double getCancellationRate() { return cancellationRate; }
        public void setCancellationRate(Double cancellationRate) { this.cancellationRate = cancellationRate; }
        public String getAverageCompletionTime() { return averageCompletionTime; }
        public void setAverageCompletionTime(String averageCompletionTime) { this.averageCompletionTime = averageCompletionTime; }
        public List<DistributionItem> getStatusDistribution() { return statusDistribution; }
        public void setStatusDistribution(List<DistributionItem> statusDistribution) { this.statusDistribution = statusDistribution; }
        public List<TimeSeriesDataPoint> getTimeline() { return timeline; }
        public void setTimeline(List<TimeSeriesDataPoint> timeline) { this.timeline = timeline; }
    }

    public static class RewardPerformance {
        private long pointsEarned;
        private long pointsRedeemed;
        private long outstandingBalance;
        private long totalTransactions;
        private long totalRedemptions;
        private List<DistributionItem> pointsBySource;
        private List<TimeSeriesDataPoint> timeline;

        public RewardPerformance() {}

        public long getPointsEarned() { return pointsEarned; }
        public void setPointsEarned(long pointsEarned) { this.pointsEarned = pointsEarned; }
        public long getPointsRedeemed() { return pointsRedeemed; }
        public void setPointsRedeemed(long pointsRedeemed) { this.pointsRedeemed = pointsRedeemed; }
        public long getOutstandingBalance() { return outstandingBalance; }
        public void setOutstandingBalance(long outstandingBalance) { this.outstandingBalance = outstandingBalance; }
        public long getTotalTransactions() { return totalTransactions; }
        public void setTotalTransactions(long totalTransactions) { this.totalTransactions = totalTransactions; }
        public long getTotalRedemptions() { return totalRedemptions; }
        public void setTotalRedemptions(long totalRedemptions) { this.totalRedemptions = totalRedemptions; }
        public List<DistributionItem> getPointsBySource() { return pointsBySource; }
        public void setPointsBySource(List<DistributionItem> pointsBySource) { this.pointsBySource = pointsBySource; }
        public List<TimeSeriesDataPoint> getTimeline() { return timeline; }
        public void setTimeline(List<TimeSeriesDataPoint> timeline) { this.timeline = timeline; }
    }

    public static class PaymentPerformance {
        private long totalTransactions;
        private long successfulCount;
        private long failedCount;
        private long pendingCount;
        private BigDecimal totalVolumeInr;
        private List<TimeSeriesDataPoint> timeline;

        public PaymentPerformance() {}

        public long getTotalTransactions() { return totalTransactions; }
        public void setTotalTransactions(long totalTransactions) { this.totalTransactions = totalTransactions; }
        public long getSuccessfulCount() { return successfulCount; }
        public void setSuccessfulCount(long successfulCount) { this.successfulCount = successfulCount; }
        public long getFailedCount() { return failedCount; }
        public void setFailedCount(long failedCount) { this.failedCount = failedCount; }
        public long getPendingCount() { return pendingCount; }
        public void setPendingCount(long pendingCount) { this.pendingCount = pendingCount; }
        public BigDecimal getTotalVolumeInr() { return totalVolumeInr; }
        public void setTotalVolumeInr(BigDecimal totalVolumeInr) { this.totalVolumeInr = totalVolumeInr; }
        public List<TimeSeriesDataPoint> getTimeline() { return timeline; }
        public void setTimeline(List<TimeSeriesDataPoint> timeline) { this.timeline = timeline; }
    }

    public static class SystemPerformance {
        private String backendStatus;
        private String databaseStatus;
        private Long databaseLatencyMs;
        private Long jvmUptimeSeconds;
        private Long jvmUsedMemoryMb;
        private Long jvmMaxMemoryMb;
        private boolean telemetryAvailable;
        private String telemetryNotice;
        private String requiredInfrastructure;

        public SystemPerformance() {}

        public String getBackendStatus() { return backendStatus; }
        public void setBackendStatus(String backendStatus) { this.backendStatus = backendStatus; }
        public String getDatabaseStatus() { return databaseStatus; }
        public void setDatabaseStatus(String databaseStatus) { this.databaseStatus = databaseStatus; }
        public Long getDatabaseLatencyMs() { return databaseLatencyMs; }
        public void setDatabaseLatencyMs(Long databaseLatencyMs) { this.databaseLatencyMs = databaseLatencyMs; }
        public Long getJvmUptimeSeconds() { return jvmUptimeSeconds; }
        public void setJvmUptimeSeconds(Long jvmUptimeSeconds) { this.jvmUptimeSeconds = jvmUptimeSeconds; }
        public Long getJvmUsedMemoryMb() { return jvmUsedMemoryMb; }
        public void setJvmUsedMemoryMb(Long jvmUsedMemoryMb) { this.jvmUsedMemoryMb = jvmUsedMemoryMb; }
        public Long getJvmMaxMemoryMb() { return jvmMaxMemoryMb; }
        public void setJvmMaxMemoryMb(Long jvmMaxMemoryMb) { this.jvmMaxMemoryMb = jvmMaxMemoryMb; }
        public boolean isTelemetryAvailable() { return telemetryAvailable; }
        public void setTelemetryAvailable(boolean telemetryAvailable) { this.telemetryAvailable = telemetryAvailable; }
        public String getTelemetryNotice() { return telemetryNotice; }
        public void setTelemetryNotice(String telemetryNotice) { this.telemetryNotice = telemetryNotice; }
        public String getRequiredInfrastructure() { return requiredInfrastructure; }
        public void setRequiredInfrastructure(String requiredInfrastructure) { this.requiredInfrastructure = requiredInfrastructure; }
    }
}

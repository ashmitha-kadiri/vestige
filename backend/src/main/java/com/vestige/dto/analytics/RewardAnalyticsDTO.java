package com.vestige.dto.analytics;

import java.util.ArrayList;
import java.util.List;

public class RewardAnalyticsDTO {
    private Long totalPointsIssued = 0L;
    private Long totalPointsRedeemed = 0L;
    private Long outstandingBalance = 0L;
    private Long totalTransactionsCount = 0L;
    private Long totalRedemptionsCount = 0L;
    private List<DistributionItem> pointsBySource = new ArrayList<>();
    private List<TimeSeriesDataPoint> issuanceOverTime = new ArrayList<>();

    public RewardAnalyticsDTO() {}

    public Long getTotalPointsIssued() {
        return totalPointsIssued;
    }

    public void setTotalPointsIssued(Long totalPointsIssued) {
        this.totalPointsIssued = totalPointsIssued;
    }

    public Long getTotalPointsRedeemed() {
        return totalPointsRedeemed;
    }

    public void setTotalPointsRedeemed(Long totalPointsRedeemed) {
        this.totalPointsRedeemed = totalPointsRedeemed;
    }

    public Long getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Long outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Long getTotalTransactionsCount() {
        return totalTransactionsCount;
    }

    public void setTotalTransactionsCount(Long totalTransactionsCount) {
        this.totalTransactionsCount = totalTransactionsCount;
    }

    public Long getTotalRedemptionsCount() {
        return totalRedemptionsCount;
    }

    public void setTotalRedemptionsCount(Long totalRedemptionsCount) {
        this.totalRedemptionsCount = totalRedemptionsCount;
    }

    public List<DistributionItem> getPointsBySource() {
        return pointsBySource;
    }

    public void setPointsBySource(List<DistributionItem> pointsBySource) {
        this.pointsBySource = pointsBySource;
    }

    public List<TimeSeriesDataPoint> getIssuanceOverTime() {
        return issuanceOverTime;
    }

    public void setIssuanceOverTime(List<TimeSeriesDataPoint> issuanceOverTime) {
        this.issuanceOverTime = issuanceOverTime;
    }
}

package com.vestige.dto.analytics;

import java.util.ArrayList;
import java.util.List;

public class RepairAnalyticsDTO {
    private Long totalBookings = 0L;
    private Long pendingCount = 0L;
    private Long acceptedCount = 0L;
    private Long inProgressCount = 0L;
    private Long completedCount = 0L;
    private Long cancelledCount = 0L;
    private Long rejectedCount = 0L;
    private Double completionRate = 0.0;
    private Double averageEstimatedCost = 0.0;
    private Long costSampleSize = 0L;
    private List<DistributionItem> statusDistribution = new ArrayList<>();
    private List<TimeSeriesDataPoint> repairsOverTime = new ArrayList<>();

    public RepairAnalyticsDTO() {}

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public Long getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(Long pendingCount) {
        this.pendingCount = pendingCount;
    }

    public Long getAcceptedCount() {
        return acceptedCount;
    }

    public void setAcceptedCount(Long acceptedCount) {
        this.acceptedCount = acceptedCount;
    }

    public Long getInProgressCount() {
        return inProgressCount;
    }

    public void setInProgressCount(Long inProgressCount) {
        this.inProgressCount = inProgressCount;
    }

    public Long getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(Long completedCount) {
        this.completedCount = completedCount;
    }

    public Long getCancelledCount() {
        return cancelledCount;
    }

    public void setCancelledCount(Long cancelledCount) {
        this.cancelledCount = cancelledCount;
    }

    public Long getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(Long rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public Double getAverageEstimatedCost() {
        return averageEstimatedCost;
    }

    public void setAverageEstimatedCost(Double averageEstimatedCost) {
        this.averageEstimatedCost = averageEstimatedCost;
    }

    public Long getCostSampleSize() {
        return costSampleSize;
    }

    public void setCostSampleSize(Long costSampleSize) {
        this.costSampleSize = costSampleSize;
    }

    public List<DistributionItem> getStatusDistribution() {
        return statusDistribution;
    }

    public void setStatusDistribution(List<DistributionItem> statusDistribution) {
        this.statusDistribution = statusDistribution;
    }

    public List<TimeSeriesDataPoint> getRepairsOverTime() {
        return repairsOverTime;
    }

    public void setRepairsOverTime(List<TimeSeriesDataPoint> repairsOverTime) {
        this.repairsOverTime = repairsOverTime;
    }
}

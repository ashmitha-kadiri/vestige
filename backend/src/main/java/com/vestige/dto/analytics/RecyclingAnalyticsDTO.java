package com.vestige.dto.analytics;

import java.util.ArrayList;
import java.util.List;

public class RecyclingAnalyticsDTO {
    private Long totalRequests = 0L;
    private Long totalDevicesRecycled = 0L;
    private Long pendingCount = 0L;
    private Long acceptedCount = 0L;
    private Long scheduledCount = 0L;
    private Long completedCount = 0L;
    private Long cancelledCount = 0L;
    private Double completionRate = 0.0;
    private List<DistributionItem> statusDistribution = new ArrayList<>();
    private List<TimeSeriesDataPoint> collectionsOverTime = new ArrayList<>();

    public RecyclingAnalyticsDTO() {}

    public Long getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(Long totalRequests) {
        this.totalRequests = totalRequests;
    }

    public Long getTotalDevicesRecycled() {
        return totalDevicesRecycled;
    }

    public void setTotalDevicesRecycled(Long totalDevicesRecycled) {
        this.totalDevicesRecycled = totalDevicesRecycled;
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

    public Long getScheduledCount() {
        return scheduledCount;
    }

    public void setScheduledCount(Long scheduledCount) {
        this.scheduledCount = scheduledCount;
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

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public List<DistributionItem> getStatusDistribution() {
        return statusDistribution;
    }

    public void setStatusDistribution(List<DistributionItem> statusDistribution) {
        this.statusDistribution = statusDistribution;
    }

    public List<TimeSeriesDataPoint> getCollectionsOverTime() {
        return collectionsOverTime;
    }

    public void setCollectionsOverTime(List<TimeSeriesDataPoint> collectionsOverTime) {
        this.collectionsOverTime = collectionsOverTime;
    }
}

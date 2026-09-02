package com.vestige.dto.analytics;

import java.util.ArrayList;
import java.util.List;

public class DeviceAnalyticsDTO {
    private Long totalSubmissions = 0L;
    private Long repairCount = 0L;
    private Long recycleCount = 0L;
    private Double averageRepairabilityScore = 0.0;
    private List<DistributionItem> categoryDistribution = new ArrayList<>();
    private List<DistributionItem> conditionDistribution = new ArrayList<>();
    private List<DistributionItem> brandDistribution = new ArrayList<>();
    private List<DistributionItem> confidenceDistribution = new ArrayList<>();
    private List<TimeSeriesDataPoint> submissionsOverTime = new ArrayList<>();

    public DeviceAnalyticsDTO() {}

    public Long getTotalSubmissions() {
        return totalSubmissions;
    }

    public void setTotalSubmissions(Long totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }

    public Long getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(Long repairCount) {
        this.repairCount = repairCount;
    }

    public Long getRecycleCount() {
        return recycleCount;
    }

    public void setRecycleCount(Long recycleCount) {
        this.recycleCount = recycleCount;
    }

    public Double getAverageRepairabilityScore() {
        return averageRepairabilityScore;
    }

    public void setAverageRepairabilityScore(Double averageRepairabilityScore) {
        this.averageRepairabilityScore = averageRepairabilityScore;
    }

    public List<DistributionItem> getCategoryDistribution() {
        return categoryDistribution;
    }

    public void setCategoryDistribution(List<DistributionItem> categoryDistribution) {
        this.categoryDistribution = categoryDistribution;
    }

    public List<DistributionItem> getConditionDistribution() {
        return conditionDistribution;
    }

    public void setConditionDistribution(List<DistributionItem> conditionDistribution) {
        this.conditionDistribution = conditionDistribution;
    }

    public List<DistributionItem> getBrandDistribution() {
        return brandDistribution;
    }

    public void setBrandDistribution(List<DistributionItem> brandDistribution) {
        this.brandDistribution = brandDistribution;
    }

    public List<DistributionItem> getConfidenceDistribution() {
        return confidenceDistribution;
    }

    public void setConfidenceDistribution(List<DistributionItem> confidenceDistribution) {
        this.confidenceDistribution = confidenceDistribution;
    }

    public List<TimeSeriesDataPoint> getSubmissionsOverTime() {
        return submissionsOverTime;
    }

    public void setSubmissionsOverTime(List<TimeSeriesDataPoint> submissionsOverTime) {
        this.submissionsOverTime = submissionsOverTime;
    }
}

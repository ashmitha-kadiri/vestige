package com.vestige.dto.analytics;

import java.util.ArrayList;
import java.util.List;

public class VendorAnalyticsDTO {
    private String businessName;
    private String verificationStatus;
    private Long assignedRepairs = 0L;
    private Long completedRepairs = 0L;
    private Long activeRepairs = 0L;
    private Double repairCompletionRate = 0.0;
    private Long assignedRecycling = 0L;
    private Long completedRecycling = 0L;
    private Long activeRecycling = 0L;
    private Double recyclingCompletionRate = 0.0;
    private List<DistributionItem> repairStatusDistribution = new ArrayList<>();
    private List<TimeSeriesDataPoint> monthlyActivity = new ArrayList<>();

    public VendorAnalyticsDTO() {}

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Long getAssignedRepairs() {
        return assignedRepairs;
    }

    public void setAssignedRepairs(Long assignedRepairs) {
        this.assignedRepairs = assignedRepairs;
    }

    public Long getCompletedRepairs() {
        return completedRepairs;
    }

    public void setCompletedRepairs(Long completedRepairs) {
        this.completedRepairs = completedRepairs;
    }

    public Long getActiveRepairs() {
        return activeRepairs;
    }

    public void setActiveRepairs(Long activeRepairs) {
        this.activeRepairs = activeRepairs;
    }

    public Double getRepairCompletionRate() {
        return repairCompletionRate;
    }

    public void setRepairCompletionRate(Double repairCompletionRate) {
        this.repairCompletionRate = repairCompletionRate;
    }

    public Long getAssignedRecycling() {
        return assignedRecycling;
    }

    public void setAssignedRecycling(Long assignedRecycling) {
        this.assignedRecycling = assignedRecycling;
    }

    public Long getCompletedRecycling() {
        return completedRecycling;
    }

    public void setCompletedRecycling(Long completedRecycling) {
        this.completedRecycling = completedRecycling;
    }

    public Long getActiveRecycling() {
        return activeRecycling;
    }

    public void setActiveRecycling(Long activeRecycling) {
        this.activeRecycling = activeRecycling;
    }

    public Double getRecyclingCompletionRate() {
        return recyclingCompletionRate;
    }

    public void setRecyclingCompletionRate(Double recyclingCompletionRate) {
        this.recyclingCompletionRate = recyclingCompletionRate;
    }

    public List<DistributionItem> getRepairStatusDistribution() {
        return repairStatusDistribution;
    }

    public void setRepairStatusDistribution(List<DistributionItem> repairStatusDistribution) {
        this.repairStatusDistribution = repairStatusDistribution;
    }

    public List<TimeSeriesDataPoint> getMonthlyActivity() {
        return monthlyActivity;
    }

    public void setMonthlyActivity(List<TimeSeriesDataPoint> monthlyActivity) {
        this.monthlyActivity = monthlyActivity;
    }
}

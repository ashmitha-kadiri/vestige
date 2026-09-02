package com.vestige.dto.analytics;

import java.util.ArrayList;
import java.util.List;

public class AdminOverviewDTO {
    private Long totalUsers = 0L;
    private Long activeUsers = 0L;
    private Long totalVendors = 0L;
    private Long verifiedVendors = 0L;
    private Long totalSubmissions = 0L;
    private Long repairRecommendations = 0L;
    private Long recycleRecommendations = 0L;
    private Long completedRepairs = 0L;
    private Long completedRecycling = 0L;
    private Double repairCompletionRate = 0.0;
    private Double recyclingCompletionRate = 0.0;
    private Long totalPointsIssued = 0L;
    private Long totalPointsRedeemed = 0L;
    private Long outstandingPointsBalance = 0L;
    private List<TimeSeriesDataPoint> activityOverTime = new ArrayList<>();

    public AdminOverviewDTO() {}

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Long getTotalVendors() {
        return totalVendors;
    }

    public void setTotalVendors(Long totalVendors) {
        this.totalVendors = totalVendors;
    }

    public Long getVerifiedVendors() {
        return verifiedVendors;
    }

    public void setVerifiedVendors(Long verifiedVendors) {
        this.verifiedVendors = verifiedVendors;
    }

    public Long getTotalSubmissions() {
        return totalSubmissions;
    }

    public void setTotalSubmissions(Long totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }

    public Long getRepairRecommendations() {
        return repairRecommendations;
    }

    public void setRepairRecommendations(Long repairRecommendations) {
        this.repairRecommendations = repairRecommendations;
    }

    public Long getRecycleRecommendations() {
        return recycleRecommendations;
    }

    public void setRecycleRecommendations(Long recycleRecommendations) {
        this.recycleRecommendations = recycleRecommendations;
    }

    public Long getCompletedRepairs() {
        return completedRepairs;
    }

    public void setCompletedRepairs(Long completedRepairs) {
        this.completedRepairs = completedRepairs;
    }

    public Long getCompletedRecycling() {
        return completedRecycling;
    }

    public void setCompletedRecycling(Long completedRecycling) {
        this.completedRecycling = completedRecycling;
    }

    public Double getRepairCompletionRate() {
        return repairCompletionRate;
    }

    public void setRepairCompletionRate(Double repairCompletionRate) {
        this.repairCompletionRate = repairCompletionRate;
    }

    public Double getRecyclingCompletionRate() {
        return recyclingCompletionRate;
    }

    public void setRecyclingCompletionRate(Double recyclingCompletionRate) {
        this.recyclingCompletionRate = recyclingCompletionRate;
    }

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

    public Long getOutstandingPointsBalance() {
        return outstandingPointsBalance;
    }

    public void setOutstandingPointsBalance(Long outstandingPointsBalance) {
        this.outstandingPointsBalance = outstandingPointsBalance;
    }

    public List<TimeSeriesDataPoint> getActivityOverTime() {
        return activityOverTime;
    }

    public void setActivityOverTime(List<TimeSeriesDataPoint> activityOverTime) {
        this.activityOverTime = activityOverTime;
    }
}

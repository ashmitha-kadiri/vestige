package com.vestige.dto.analytics;

import java.util.ArrayList;
import java.util.List;

public class UserAnalyticsDTO {
    private Long totalSubmissions = 0L;
    private Long repairRecommendations = 0L;
    private Long recycleRecommendations = 0L;
    private Long totalRepairsBooked = 0L;
    private Long completedRepairs = 0L;
    private Long totalRecyclingRequests = 0L;
    private Long completedRecycling = 0L;
    private Long totalDevicesRecycled = 0L;
    private Long currentPointsBalance = 0L;
    private Long lifetimePointsEarned = 0L;
    private Long lifetimePointsRedeemed = 0L;
    private List<TimeSeriesDataPoint> personalActivityTimeline = new ArrayList<>();

    public UserAnalyticsDTO() {}

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

    public Long getTotalRepairsBooked() {
        return totalRepairsBooked;
    }

    public void setTotalRepairsBooked(Long totalRepairsBooked) {
        this.totalRepairsBooked = totalRepairsBooked;
    }

    public Long getCompletedRepairs() {
        return completedRepairs;
    }

    public void setCompletedRepairs(Long completedRepairs) {
        this.completedRepairs = completedRepairs;
    }

    public Long getTotalRecyclingRequests() {
        return totalRecyclingRequests;
    }

    public void setTotalRecyclingRequests(Long totalRecyclingRequests) {
        this.totalRecyclingRequests = totalRecyclingRequests;
    }

    public Long getCompletedRecycling() {
        return completedRecycling;
    }

    public void setCompletedRecycling(Long completedRecycling) {
        this.completedRecycling = completedRecycling;
    }

    public Long getTotalDevicesRecycled() {
        return totalDevicesRecycled;
    }

    public void setTotalDevicesRecycled(Long totalDevicesRecycled) {
        this.totalDevicesRecycled = totalDevicesRecycled;
    }

    public Long getCurrentPointsBalance() {
        return currentPointsBalance;
    }

    public void setCurrentPointsBalance(Long currentPointsBalance) {
        this.currentPointsBalance = currentPointsBalance;
    }

    public Long getLifetimePointsEarned() {
        return lifetimePointsEarned;
    }

    public void setLifetimePointsEarned(Long lifetimePointsEarned) {
        this.lifetimePointsEarned = lifetimePointsEarned;
    }

    public Long getLifetimePointsRedeemed() {
        return lifetimePointsRedeemed;
    }

    public void setLifetimePointsRedeemed(Long lifetimePointsRedeemed) {
        this.lifetimePointsRedeemed = lifetimePointsRedeemed;
    }

    public List<TimeSeriesDataPoint> getPersonalActivityTimeline() {
        return personalActivityTimeline;
    }

    public void setPersonalActivityTimeline(List<TimeSeriesDataPoint> personalActivityTimeline) {
        this.personalActivityTimeline = personalActivityTimeline;
    }
}

package com.vestige.dto.admin;

import com.vestige.dto.analytics.TimeSeriesDataPoint;
import java.util.List;

public class RegistrationActivityDTO {

    private long currentPeriodRegistrations;
    private Long previousPeriodRegistrations;
    private Double growthPercentage;
    private String comparisonMessage;
    private List<TimeSeriesDataPoint> timeline;

    public RegistrationActivityDTO() {
    }

    public RegistrationActivityDTO(long currentPeriodRegistrations, Long previousPeriodRegistrations,
                                   Double growthPercentage, String comparisonMessage,
                                   List<TimeSeriesDataPoint> timeline) {
        this.currentPeriodRegistrations = currentPeriodRegistrations;
        this.previousPeriodRegistrations = previousPeriodRegistrations;
        this.growthPercentage = growthPercentage;
        this.comparisonMessage = comparisonMessage;
        this.timeline = timeline;
    }

    public long getCurrentPeriodRegistrations() {
        return currentPeriodRegistrations;
    }

    public void setCurrentPeriodRegistrations(long currentPeriodRegistrations) {
        this.currentPeriodRegistrations = currentPeriodRegistrations;
    }

    public Long getPreviousPeriodRegistrations() {
        return previousPeriodRegistrations;
    }

    public void setPreviousPeriodRegistrations(Long previousPeriodRegistrations) {
        this.previousPeriodRegistrations = previousPeriodRegistrations;
    }

    public Double getGrowthPercentage() {
        return growthPercentage;
    }

    public void setGrowthPercentage(Double growthPercentage) {
        this.growthPercentage = growthPercentage;
    }

    public String getComparisonMessage() {
        return comparisonMessage;
    }

    public void setComparisonMessage(String comparisonMessage) {
        this.comparisonMessage = comparisonMessage;
    }

    public List<TimeSeriesDataPoint> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<TimeSeriesDataPoint> timeline) {
        this.timeline = timeline;
    }
}

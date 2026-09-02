package com.vestige.dto.analytics;

public class TimeSeriesDataPoint {
    private String label;
    private Long count;
    private Double value;

    public TimeSeriesDataPoint() {}

    public TimeSeriesDataPoint(String label, Long count) {
        this.label = label;
        this.count = count;
        this.value = count != null ? count.doubleValue() : 0.0;
    }

    public TimeSeriesDataPoint(String label, Long count, Double value) {
        this.label = label;
        this.count = count;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

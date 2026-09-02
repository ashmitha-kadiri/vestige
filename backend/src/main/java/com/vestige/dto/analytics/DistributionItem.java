package com.vestige.dto.analytics;

public class DistributionItem {
    private String key;
    private String label;
    private Long count;
    private Double percentage;

    public DistributionItem() {}

    public DistributionItem(String key, String label, Long count, Double percentage) {
        this.key = key;
        this.label = label;
        this.count = count;
        this.percentage = percentage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}

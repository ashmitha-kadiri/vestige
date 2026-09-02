package com.vestige.dto.request;

import com.vestige.model.enums.DeviceCategoryType;
import com.vestige.model.enums.DeviceConditionGrade;
import com.vestige.model.enums.PartAvailabilityStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceAssessmentRequest {

    private UUID userId;

    @NotNull(message = "Device type category is required")
    private DeviceCategoryType deviceType;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model name/number is required")
    private String model;

    @NotNull(message = "Device age in years is required")
    @Min(value = 0, message = "Device age cannot be negative")
    private Integer deviceAgeYears;

    @NotNull(message = "Condition grade is required")
    private DeviceConditionGrade condition;

    private List<String> knownIssues = new ArrayList<>();

    private BigDecimal estimatedRepairCost = BigDecimal.ZERO;

    private BigDecimal originalValue = BigDecimal.ZERO;

    private PartAvailabilityStatus partAvailability = PartAvailabilityStatus.UNKNOWN;

    public DeviceAssessmentRequest() {}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public DeviceCategoryType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceCategoryType deviceType) {
        this.deviceType = deviceType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getDeviceAgeYears() {
        return deviceAgeYears;
    }

    public void setDeviceAgeYears(Integer deviceAgeYears) {
        this.deviceAgeYears = deviceAgeYears;
    }

    public DeviceConditionGrade getCondition() {
        return condition;
    }

    public void setCondition(DeviceConditionGrade condition) {
        this.condition = condition;
    }

    public List<String> getKnownIssues() {
        return knownIssues;
    }

    public void setKnownIssues(List<String> knownIssues) {
        this.knownIssues = knownIssues;
    }

    public BigDecimal getEstimatedRepairCost() {
        return estimatedRepairCost;
    }

    public void setEstimatedRepairCost(BigDecimal estimatedRepairCost) {
        this.estimatedRepairCost = estimatedRepairCost;
    }

    public BigDecimal getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(BigDecimal originalValue) {
        this.originalValue = originalValue;
    }

    public PartAvailabilityStatus getPartAvailability() {
        return partAvailability;
    }

    public void setPartAvailability(PartAvailabilityStatus partAvailability) {
        this.partAvailability = partAvailability;
    }
}

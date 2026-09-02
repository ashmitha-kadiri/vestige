package com.vestige.dto.response;

import com.vestige.model.enums.DeviceCategoryType;
import com.vestige.model.enums.DeviceConditionGrade;
import com.vestige.model.enums.EngineConfidenceLevel;
import com.vestige.model.enums.EngineRecommendationType;
import com.vestige.model.enums.PartAvailabilityStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class DeviceAssessmentResponse {

    private UUID id;
    private UUID userId;
    private DeviceCategoryType deviceType;
    private String brand;
    private String model;
    private Integer deviceAgeYears;
    private DeviceConditionGrade condition;
    private List<String> knownIssues;
    private BigDecimal estimatedRepairCost;
    private BigDecimal originalValue;
    private PartAvailabilityStatus partAvailability;
    private Integer engineScore;
    private EngineRecommendationType engineRecommendation;
    private EngineConfidenceLevel engineConfidence;
    private String engineRationale;
    private OffsetDateTime createdAt;

    public DeviceAssessmentResponse() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public Integer getEngineScore() {
        return engineScore;
    }

    public void setEngineScore(Integer engineScore) {
        this.engineScore = engineScore;
    }

    public EngineRecommendationType getEngineRecommendation() {
        return engineRecommendation;
    }

    public void setEngineRecommendation(EngineRecommendationType engineRecommendation) {
        this.engineRecommendation = engineRecommendation;
    }

    public EngineConfidenceLevel getEngineConfidence() {
        return engineConfidence;
    }

    public void setEngineConfidence(EngineConfidenceLevel engineConfidence) {
        this.engineConfidence = engineConfidence;
    }

    public String getEngineRationale() {
        return engineRationale;
    }

    public void setEngineRationale(String engineRationale) {
        this.engineRationale = engineRationale;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

package com.vestige.model;

import com.vestige.model.enums.DeviceCategoryType;
import com.vestige.model.enums.DeviceConditionGrade;
import com.vestige.model.enums.EngineConfidenceLevel;
import com.vestige.model.enums.EngineRecommendationType;
import com.vestige.model.enums.PartAvailabilityStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "device_submissions")
public class DeviceSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    private DeviceCategoryType deviceType;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "device_age_years", nullable = false)
    private Integer deviceAgeYears;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false)
    private DeviceConditionGrade condition;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "known_issues")
    private List<String> knownIssues = new ArrayList<>();

    @Column(name = "estimated_repair_cost", precision = 10, scale = 2, nullable = false)
    private BigDecimal estimatedRepairCost = BigDecimal.ZERO;

    @Column(name = "original_value", precision = 10, scale = 2, nullable = false)
    private BigDecimal originalValue = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "part_availability", nullable = false)
    private PartAvailabilityStatus partAvailability = PartAvailabilityStatus.UNKNOWN;

    @Column(name = "engine_score")
    private Integer engineScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine_recommendation")
    private EngineRecommendationType engineRecommendation;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine_confidence")
    private EngineConfidenceLevel engineConfidence;

    @Column(name = "engine_rationale", columnDefinition = "TEXT")
    private String engineRationale;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public DeviceSubmission() {}

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
        if (knownIssues == null) knownIssues = new ArrayList<>();
        if (estimatedRepairCost == null) estimatedRepairCost = BigDecimal.ZERO;
        if (originalValue == null) originalValue = BigDecimal.ZERO;
        if (partAvailability == null) partAvailability = PartAvailabilityStatus.UNKNOWN;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

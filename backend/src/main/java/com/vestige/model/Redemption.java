package com.vestige.model;

import com.vestige.model.enums.RedemptionStatusType;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "redemptions")
public class Redemption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "reward_item", nullable = false, length = 150)
    private String rewardItem;

    @Column(name = "points_used", nullable = false)
    private Integer pointsUsed;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RedemptionStatusType status = RedemptionStatusType.PENDING;

    @Column(name = "fulfillment_notes", columnDefinition = "TEXT")
    private String fulfillmentNotes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public Redemption() {}

    public Redemption(User user, String rewardItem, Integer pointsUsed, RedemptionStatusType status, String fulfillmentNotes) {
        this.user = user;
        this.rewardItem = rewardItem;
        this.pointsUsed = pointsUsed;
        this.status = status;
        this.fulfillmentNotes = fulfillmentNotes;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
        if (updatedAt == null) updatedAt = OffsetDateTime.now();
        if (status == null) status = RedemptionStatusType.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
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

    public String getRewardItem() {
        return rewardItem;
    }

    public void setRewardItem(String rewardItem) {
        this.rewardItem = rewardItem;
    }

    public Integer getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(Integer pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    public RedemptionStatusType getStatus() {
        return status;
    }

    public void setStatus(RedemptionStatusType status) {
        this.status = status;
    }

    public String getFulfillmentNotes() {
        return fulfillmentNotes;
    }

    public void setFulfillmentNotes(String fulfillmentNotes) {
        this.fulfillmentNotes = fulfillmentNotes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

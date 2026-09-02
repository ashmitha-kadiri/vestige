package com.vestige.dto.response;

import com.vestige.model.enums.RedemptionStatusType;

import java.time.OffsetDateTime;
import java.util.UUID;

public class RedemptionResponse {

    private UUID id;
    private UUID userId;
    private String rewardItem;
    private Integer pointsUsed;
    private RedemptionStatusType status;
    private String fulfillmentNotes;
    private OffsetDateTime createdAt;

    public RedemptionResponse() {}

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
}

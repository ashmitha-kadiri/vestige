package com.vestige.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class RedeemRewardRequestDTO {

    private UUID userId;

    @NotBlank(message = "Reward item name is required")
    private String rewardItem;

    @NotNull(message = "Points cost is required")
    @Min(value = 1, message = "Points must be greater than 0")
    private Integer points;

    private String deliveryNotes;

    public RedeemRewardRequestDTO() {}

    public RedeemRewardRequestDTO(UUID userId, String rewardItem, Integer points, String deliveryNotes) {
        this.userId = userId;
        this.rewardItem = rewardItem;
        this.points = points;
        this.deliveryNotes = deliveryNotes;
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }
}

package com.vestige.dto.request;

import com.vestige.model.enums.RecyclingStatusType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class RecyclingStatusUpdateDTO {

    @NotNull(message = "New status is required")
    private RecyclingStatusType status;

    private UUID changedByUserId;

    private String notes;

    private Integer pointsToAward;

    public RecyclingStatusUpdateDTO() {}

    public RecyclingStatusType getStatus() {
        return status;
    }

    public void setStatus(RecyclingStatusType status) {
        this.status = status;
    }

    public UUID getChangedByUserId() {
        return changedByUserId;
    }

    public void setChangedByUserId(UUID changedByUserId) {
        this.changedByUserId = changedByUserId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getPointsToAward() {
        return pointsToAward;
    }

    public void setPointsToAward(Integer pointsToAward) {
        this.pointsToAward = pointsToAward;
    }
}

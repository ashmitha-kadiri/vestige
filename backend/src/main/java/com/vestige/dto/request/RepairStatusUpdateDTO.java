package com.vestige.dto.request;

import com.vestige.model.enums.BookingStatusType;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class RepairStatusUpdateDTO {

    @NotNull(message = "Target status is required")
    private BookingStatusType status;

    private UUID changedByUserId;
    private String notes;
    private String rejectionReason;
    private Integer userRating;
    private String userFeedback;

    public RepairStatusUpdateDTO() {}

    public BookingStatusType getStatus() {
        return status;
    }

    public void setStatus(BookingStatusType status) {
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

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(String userFeedback) {
        this.userFeedback = userFeedback;
    }
}

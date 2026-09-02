package com.vestige.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class AdminProvisionRequest {

    @NotNull(message = "Target user ID is required")
    private UUID targetUserId;

    private String adminNotes;

    public AdminProvisionRequest() {
    }

    public AdminProvisionRequest(UUID targetUserId, String adminNotes) {
        this.targetUserId = targetUserId;
        this.adminNotes = adminNotes;
    }

    public UUID getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(UUID targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }
}

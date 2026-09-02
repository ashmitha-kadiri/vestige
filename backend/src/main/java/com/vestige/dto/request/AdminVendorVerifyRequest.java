package com.vestige.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AdminVendorVerifyRequest {

    @NotBlank(message = "Action is required ('APPROVE' or 'REJECT')")
    private String action;

    private String rejectionReason;

    public AdminVendorVerifyRequest() {
    }

    public AdminVendorVerifyRequest(String action, String rejectionReason) {
        this.action = action;
        this.rejectionReason = rejectionReason;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}

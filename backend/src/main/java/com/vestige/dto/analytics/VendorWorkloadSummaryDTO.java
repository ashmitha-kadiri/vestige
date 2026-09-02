package com.vestige.dto.analytics;

import java.util.UUID;

public class VendorWorkloadSummaryDTO {
    private UUID vendorId;
    private String businessName;
    private String verificationStatus;
    private Long assignedRepairs = 0L;
    private Long completedRepairs = 0L;
    private Double repairCompletionRate = 0.0;
    private Long assignedRecycling = 0L;
    private Long completedRecycling = 0L;
    private Double recyclingCompletionRate = 0.0;

    public VendorWorkloadSummaryDTO() {}

    public VendorWorkloadSummaryDTO(UUID vendorId, String businessName, String verificationStatus,
                                   Long assignedRepairs, Long completedRepairs, Double repairCompletionRate,
                                   Long assignedRecycling, Long completedRecycling, Double recyclingCompletionRate) {
        this.vendorId = vendorId;
        this.businessName = businessName;
        this.verificationStatus = verificationStatus;
        this.assignedRepairs = assignedRepairs != null ? assignedRepairs : 0L;
        this.completedRepairs = completedRepairs != null ? completedRepairs : 0L;
        this.repairCompletionRate = repairCompletionRate != null ? repairCompletionRate : 0.0;
        this.assignedRecycling = assignedRecycling != null ? assignedRecycling : 0L;
        this.completedRecycling = completedRecycling != null ? completedRecycling : 0L;
        this.recyclingCompletionRate = recyclingCompletionRate != null ? recyclingCompletionRate : 0.0;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Long getAssignedRepairs() {
        return assignedRepairs;
    }

    public void setAssignedRepairs(Long assignedRepairs) {
        this.assignedRepairs = assignedRepairs;
    }

    public Long getCompletedRepairs() {
        return completedRepairs;
    }

    public void setCompletedRepairs(Long completedRepairs) {
        this.completedRepairs = completedRepairs;
    }

    public Double getRepairCompletionRate() {
        return repairCompletionRate;
    }

    public void setRepairCompletionRate(Double repairCompletionRate) {
        this.repairCompletionRate = repairCompletionRate;
    }

    public Long getAssignedRecycling() {
        return assignedRecycling;
    }

    public void setAssignedRecycling(Long assignedRecycling) {
        this.assignedRecycling = assignedRecycling;
    }

    public Long getCompletedRecycling() {
        return completedRecycling;
    }

    public void setCompletedRecycling(Long completedRecycling) {
        this.completedRecycling = completedRecycling;
    }

    public Double getRecyclingCompletionRate() {
        return recyclingCompletionRate;
    }

    public void setRecyclingCompletionRate(Double recyclingCompletionRate) {
        this.recyclingCompletionRate = recyclingCompletionRate;
    }
}

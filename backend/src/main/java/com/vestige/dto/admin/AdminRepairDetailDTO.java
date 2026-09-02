package com.vestige.dto.admin;

import com.vestige.model.RepairBooking;
import com.vestige.model.enums.BookingStatusType;
import com.vestige.model.enums.DeviceCategoryType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AdminRepairDetailDTO {

    private UUID id;
    private UUID userId;
    private String userFullName;
    private String userEmail;
    private UUID vendorId;
    private String vendorBusinessName;
    private UUID submissionId;
    private DeviceCategoryType deviceCategory;
    private String deviceBrand;
    private String deviceModel;
    private BigDecimal estimatedRepairCost;
    private LocalDate preferredDate;
    private BookingStatusType status;
    private String issueDescription;
    private String rejectionReason;
    private Integer userRating;
    private String userFeedback;
    private OffsetDateTime createdAt;

    public static AdminRepairDetailDTO fromEntity(RepairBooking booking) {
        AdminRepairDetailDTO dto = new AdminRepairDetailDTO();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getId());
        dto.setUserFullName(booking.getUser().getFullName());
        dto.setUserEmail(booking.getUser().getEmail());
        dto.setVendorId(booking.getVendor().getId());
        dto.setVendorBusinessName(booking.getVendor().getBusinessName());
        dto.setSubmissionId(booking.getSubmission().getId());
        dto.setDeviceCategory(booking.getSubmission().getDeviceType());
        dto.setDeviceBrand(booking.getSubmission().getBrand());
        dto.setDeviceModel(booking.getSubmission().getModel());
        dto.setEstimatedRepairCost(booking.getSubmission().getEstimatedRepairCost());
        dto.setPreferredDate(booking.getPreferredDate());
        dto.setStatus(booking.getStatus());
        dto.setIssueDescription(booking.getIssueDescription());
        dto.setRejectionReason(booking.getRejectionReason());
        dto.setUserRating(booking.getUserRating());
        dto.setUserFeedback(booking.getUserFeedback());
        dto.setCreatedAt(booking.getCreatedAt());
        return dto;
    }

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

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorBusinessName() {
        return vendorBusinessName;
    }

    public void setVendorBusinessName(String vendorBusinessName) {
        this.vendorBusinessName = vendorBusinessName;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    public DeviceCategoryType getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(DeviceCategoryType deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public BigDecimal getEstimatedRepairCost() {
        return estimatedRepairCost;
    }

    public void setEstimatedRepairCost(BigDecimal estimatedRepairCost) {
        this.estimatedRepairCost = estimatedRepairCost;
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }

    public BookingStatusType getStatus() {
        return status;
    }

    public void setStatus(BookingStatusType status) {
        this.status = status;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

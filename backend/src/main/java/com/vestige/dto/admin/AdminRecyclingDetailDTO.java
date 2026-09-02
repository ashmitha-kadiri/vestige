package com.vestige.dto.admin;

import com.vestige.model.RecyclingRequest;
import com.vestige.model.enums.DeviceCategoryType;
import com.vestige.model.enums.RecyclingStatusType;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AdminRecyclingDetailDTO {

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
    private String pickupAddress;
    private LocalDate pickupDate;
    private Integer deviceCount;
    private RecyclingStatusType status;
    private Integer pointsAwarded;
    private OffsetDateTime createdAt;

    public static AdminRecyclingDetailDTO fromEntity(RecyclingRequest request) {
        AdminRecyclingDetailDTO dto = new AdminRecyclingDetailDTO();
        dto.setId(request.getId());
        dto.setUserId(request.getUser().getId());
        dto.setUserFullName(request.getUser().getFullName());
        dto.setUserEmail(request.getUser().getEmail());
        dto.setVendorId(request.getVendor().getId());
        dto.setVendorBusinessName(request.getVendor().getBusinessName());
        dto.setSubmissionId(request.getSubmission().getId());
        dto.setDeviceCategory(request.getSubmission().getDeviceType());
        dto.setDeviceBrand(request.getSubmission().getBrand());
        dto.setDeviceModel(request.getSubmission().getModel());
        dto.setPickupAddress(request.getPickupAddress());
        dto.setPickupDate(request.getPickupDate());
        dto.setDeviceCount(request.getDeviceCount());
        dto.setStatus(request.getStatus());
        dto.setPointsAwarded(request.getPointsAwarded());
        dto.setCreatedAt(request.getCreatedAt());
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

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public RecyclingStatusType getStatus() {
        return status;
    }

    public void setStatus(RecyclingStatusType status) {
        this.status = status;
    }

    public Integer getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(Integer pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

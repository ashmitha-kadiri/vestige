package com.vestige.dto.response;

import com.vestige.model.enums.VendorVerificationStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class VendorSummaryResponse {

    private UUID id;
    private UUID userId;
    private String businessName;
    private String businessType;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String whatsappNumber;
    private List<String> serviceTypes;
    private List<String> deviceCategories;
    private VendorVerificationStatus verificationStatus;
    private BigDecimal ratingAvg;
    private Integer ratingCount;

    public VendorSummaryResponse() {}

    public static VendorSummaryResponse fromEntity(com.vestige.model.VendorProfile profile) {
        if (profile == null) return null;
        VendorSummaryResponse dto = new VendorSummaryResponse();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser() != null ? profile.getUser().getId() : null);
        dto.setBusinessName(profile.getBusinessName());
        dto.setBusinessType(profile.getBusinessType());
        dto.setAddress(profile.getAddress());
        dto.setCity(profile.getCity());
        dto.setState(profile.getState());
        dto.setPincode(profile.getPincode());
        dto.setWhatsappNumber(profile.getWhatsappNumber());
        dto.setServiceTypes(profile.getServiceTypes());
        dto.setDeviceCategories(profile.getDeviceCategories());
        dto.setVerificationStatus(profile.getVerificationStatus());
        dto.setRatingAvg(profile.getRatingAvg());
        dto.setRatingCount(profile.getRatingCount());
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public List<String> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public List<String> getDeviceCategories() {
        return deviceCategories;
    }

    public void setDeviceCategories(List<String> deviceCategories) {
        this.deviceCategories = deviceCategories;
    }

    public VendorVerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VendorVerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public BigDecimal getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(BigDecimal ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }
}

package com.vestige.dto.response;

import com.vestige.model.enums.PreferredLanguage;
import com.vestige.model.enums.UserRole;

import java.util.UUID;

public class AuthMeResponse {

    private UUID id;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private PreferredLanguage preferredLanguage;
    private boolean active;
    private Integer rewardBalance;
    private VendorSummaryResponse vendorProfile;

    public AuthMeResponse() {
    }

    public AuthMeResponse(UUID id, String email, String fullName, String phone, UserRole role,
                          PreferredLanguage preferredLanguage, boolean active, Integer rewardBalance,
                          VendorSummaryResponse vendorProfile) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.role = role;
        this.preferredLanguage = preferredLanguage;
        this.active = active;
        this.rewardBalance = rewardBalance;
        this.vendorProfile = vendorProfile;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public PreferredLanguage getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(PreferredLanguage preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getRewardBalance() {
        return rewardBalance;
    }

    public void setRewardBalance(Integer rewardBalance) {
        this.rewardBalance = rewardBalance;
    }

    public VendorSummaryResponse getVendorProfile() {
        return vendorProfile;
    }

    public void setVendorProfile(VendorSummaryResponse vendorProfile) {
        this.vendorProfile = vendorProfile;
    }
}

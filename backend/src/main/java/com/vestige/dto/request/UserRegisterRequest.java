package com.vestige.dto.request;

import com.vestige.model.enums.PreferredLanguage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    private String phone;

    private PreferredLanguage preferredLanguage = PreferredLanguage.en;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String fullName, String phone, PreferredLanguage preferredLanguage) {
        this.fullName = fullName;
        this.phone = phone;
        this.preferredLanguage = preferredLanguage != null ? preferredLanguage : PreferredLanguage.en;
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

    public PreferredLanguage getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(PreferredLanguage preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }
}

package com.vestige.dto.admin;

import com.vestige.model.RewardAccount;

import java.time.OffsetDateTime;
import java.util.UUID;

public class AdminRewardAccountDTO {

    private UUID id;
    private UUID userId;
    private String userFullName;
    private String userEmail;
    private Integer balance;
    private Integer lifetimeEarned;
    private Integer lifetimeRedeemed;
    private OffsetDateTime updatedAt;

    public static AdminRewardAccountDTO fromEntity(RewardAccount account) {
        AdminRewardAccountDTO dto = new AdminRewardAccountDTO();
        dto.setId(account.getId());
        dto.setUserId(account.getUser().getId());
        dto.setUserFullName(account.getUser().getFullName());
        dto.setUserEmail(account.getUser().getEmail());
        dto.setBalance(account.getBalance());
        dto.setLifetimeEarned(account.getLifetimeEarned());
        dto.setLifetimeRedeemed(account.getLifetimeRedeemed());
        dto.setUpdatedAt(account.getUpdatedAt());
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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getLifetimeEarned() {
        return lifetimeEarned;
    }

    public void setLifetimeEarned(Integer lifetimeEarned) {
        this.lifetimeEarned = lifetimeEarned;
    }

    public Integer getLifetimeRedeemed() {
        return lifetimeRedeemed;
    }

    public void setLifetimeRedeemed(Integer lifetimeRedeemed) {
        this.lifetimeRedeemed = lifetimeRedeemed;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

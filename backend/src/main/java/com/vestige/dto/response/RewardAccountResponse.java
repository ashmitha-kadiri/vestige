package com.vestige.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public class RewardAccountResponse {

    private UUID id;
    private UUID userId;
    private String userName;
    private Integer balance;
    private Integer lifetimeEarned;
    private Integer lifetimeRedeemed;
    private OffsetDateTime updatedAt;

    public RewardAccountResponse() {}

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

package com.vestige.dto.response;

import com.vestige.model.enums.RewardSourceType;
import com.vestige.model.enums.RewardTransactionType;

import java.time.OffsetDateTime;
import java.util.UUID;

public class RewardTransactionResponse {

    private UUID id;
    private UUID accountId;
    private Integer points;
    private RewardTransactionType transactionType;
    private RewardSourceType source;
    private UUID referenceId;
    private String description;
    private OffsetDateTime createdAt;

    public RewardTransactionResponse() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public RewardTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(RewardTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public RewardSourceType getSource() {
        return source;
    }

    public void setSource(RewardSourceType source) {
        this.source = source;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(UUID referenceId) {
        this.referenceId = referenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

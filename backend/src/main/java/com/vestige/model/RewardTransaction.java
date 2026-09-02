package com.vestige.model;

import com.vestige.model.enums.RewardSourceType;
import com.vestige.model.enums.RewardTransactionType;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "reward_transactions")
public class RewardTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private RewardAccount account;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private RewardTransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private RewardSourceType source;

    @Column(name = "reference_id")
    private UUID referenceId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public RewardTransaction() {}

    public RewardTransaction(RewardAccount account, Integer points, RewardTransactionType transactionType, RewardSourceType source, UUID referenceId, String description) {
        this.account = account;
        this.points = points;
        this.transactionType = transactionType;
        this.source = source;
        this.referenceId = referenceId;
        this.description = description;
        this.createdAt = OffsetDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RewardAccount getAccount() {
        return account;
    }

    public void setAccount(RewardAccount account) {
        this.account = account;
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

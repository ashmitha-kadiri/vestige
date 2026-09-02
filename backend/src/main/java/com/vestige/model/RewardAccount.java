package com.vestige.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "reward_accounts")
public class RewardAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "balance", nullable = false)
    private Integer balance = 0;

    @Column(name = "lifetime_earned", nullable = false)
    private Integer lifetimeEarned = 0;

    @Column(name = "lifetime_redeemed", nullable = false)
    private Integer lifetimeRedeemed = 0;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public RewardAccount() {}

    public RewardAccount(User user) {
        this.user = user;
        this.balance = 0;
        this.lifetimeEarned = 0;
        this.lifetimeRedeemed = 0;
        this.updatedAt = OffsetDateTime.now();
    }

    @PrePersist
    @PreUpdate
    protected void onPersistOrUpdate() {
        if (updatedAt == null) updatedAt = OffsetDateTime.now();
        if (balance == null) balance = 0;
        if (lifetimeEarned == null) lifetimeEarned = 0;
        if (lifetimeRedeemed == null) lifetimeRedeemed = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

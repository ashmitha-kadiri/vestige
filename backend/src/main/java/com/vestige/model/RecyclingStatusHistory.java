package com.vestige.model;

import com.vestige.model.enums.RecyclingStatusType;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "recycling_status_history")
public class RecyclingStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private RecyclingRequest request;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private RecyclingStatusType previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private RecyclingStatusType newStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by_user_id", nullable = false)
    private User changedByUser;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public RecyclingStatusHistory() {}

    public RecyclingStatusHistory(RecyclingRequest request, RecyclingStatusType previousStatus, RecyclingStatusType newStatus, User changedByUser, String notes) {
        this.request = request;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.changedByUser = changedByUser;
        this.notes = notes;
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

    public RecyclingRequest getRequest() {
        return request;
    }

    public void setRequest(RecyclingRequest request) {
        this.request = request;
    }

    public RecyclingStatusType getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(RecyclingStatusType previousStatus) {
        this.previousStatus = previousStatus;
    }

    public RecyclingStatusType getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(RecyclingStatusType newStatus) {
        this.newStatus = newStatus;
    }

    public User getChangedByUser() {
        return changedByUser;
    }

    public void setChangedByUser(User changedByUser) {
        this.changedByUser = changedByUser;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

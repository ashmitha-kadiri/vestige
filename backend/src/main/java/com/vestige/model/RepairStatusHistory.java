package com.vestige.model;

import com.vestige.model.enums.BookingStatusType;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "repair_status_history", schema = "public")
public class RepairStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private RepairBooking booking;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", length = 30)
    private BookingStatusType previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 30)
    private BookingStatusType newStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by_user_id", nullable = false)
    private User changedByUser;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public RepairStatusHistory() {}

    public RepairStatusHistory(RepairBooking booking, BookingStatusType previousStatus,
                               BookingStatusType newStatus, User changedByUser, String notes) {
        this.booking = booking;
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

    public RepairBooking getBooking() {
        return booking;
    }

    public void setBooking(RepairBooking booking) {
        this.booking = booking;
    }

    public BookingStatusType getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(BookingStatusType previousStatus) {
        this.previousStatus = previousStatus;
    }

    public BookingStatusType getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(BookingStatusType newStatus) {
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

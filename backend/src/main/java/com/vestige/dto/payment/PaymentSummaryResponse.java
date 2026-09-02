package com.vestige.dto.payment;

import com.vestige.model.Payment;
import com.vestige.model.enums.PaymentStatusType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PaymentSummaryResponse {

    private UUID id;
    private UUID userId;
    private String userEmail;
    private String userFullName;
    private String relatedEntityType;
    private UUID relatedEntityId;
    private String provider;
    private String providerOrderId;
    private String providerPaymentId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatusType status;
    private String failureReason;
    private OffsetDateTime createdAt;

    public static PaymentSummaryResponse fromEntity(Payment payment) {
        PaymentSummaryResponse dto = new PaymentSummaryResponse();
        dto.setId(payment.getId());
        dto.setUserId(payment.getUser().getId());
        dto.setUserEmail(payment.getUser().getEmail());
        dto.setUserFullName(payment.getUser().getFullName());
        dto.setRelatedEntityType(payment.getRelatedEntityType());
        dto.setRelatedEntityId(payment.getRelatedEntityId());
        dto.setProvider(payment.getProvider());
        dto.setProviderOrderId(payment.getProviderOrderId());
        dto.setProviderPaymentId(payment.getProviderPaymentId());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setStatus(payment.getStatus());
        dto.setFailureReason(payment.getFailureReason());
        dto.setCreatedAt(payment.getCreatedAt());
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }

    public UUID getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(UUID relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderOrderId() {
        return providerOrderId;
    }

    public void setProviderOrderId(String providerOrderId) {
        this.providerOrderId = providerOrderId;
    }

    public String getProviderPaymentId() {
        return providerPaymentId;
    }

    public void setProviderPaymentId(String providerPaymentId) {
        this.providerPaymentId = providerPaymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentStatusType getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusType status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

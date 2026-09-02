package com.vestige.dto.payment;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentOrderResponse {

    private UUID paymentId;
    private String providerOrderId;
    private BigDecimal amount;
    private String currency;
    private String razorpayKeyId;
    private String businessName;
    private String description;
    private String customerEmail;
    private String customerPhone;

    public PaymentOrderResponse() {
    }

    public PaymentOrderResponse(UUID paymentId, String providerOrderId, BigDecimal amount, String currency,
                                String razorpayKeyId, String businessName, String description,
                                String customerEmail, String customerPhone) {
        this.paymentId = paymentId;
        this.providerOrderId = providerOrderId;
        this.amount = amount;
        this.currency = currency;
        this.razorpayKeyId = razorpayKeyId;
        this.businessName = businessName;
        this.description = description;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public String getProviderOrderId() {
        return providerOrderId;
    }

    public void setProviderOrderId(String providerOrderId) {
        this.providerOrderId = providerOrderId;
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

    public String getRazorpayKeyId() {
        return razorpayKeyId;
    }

    public void setRazorpayKeyId(String razorpayKeyId) {
        this.razorpayKeyId = razorpayKeyId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}

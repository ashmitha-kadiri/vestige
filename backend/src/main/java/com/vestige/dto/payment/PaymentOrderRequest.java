package com.vestige.dto.payment;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class PaymentOrderRequest {

    @NotNull(message = "Booking ID is required")
    private UUID bookingId;

    public PaymentOrderRequest() {
    }

    public PaymentOrderRequest(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }
}

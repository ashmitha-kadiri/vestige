package com.vestige.controller;

import com.vestige.dto.payment.PaymentOrderRequest;
import com.vestige.dto.payment.PaymentOrderResponse;
import com.vestige.dto.payment.PaymentSummaryResponse;
import com.vestige.dto.payment.PaymentVerifyRequest;
import com.vestige.dto.response.ApiResponse;
import com.vestige.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PaymentOrderResponse>> createPaymentOrder(
            @Valid @RequestBody PaymentOrderRequest request) {
        PaymentOrderResponse response = paymentService.createRepairPaymentOrder(request.getBookingId());
        return ResponseEntity.ok(ApiResponse.ok("Razorpay payment order initialized successfully", response));
    }

    @PostMapping("/verify")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<PaymentSummaryResponse>> verifyPayment(
            @Valid @RequestBody PaymentVerifyRequest request) {
        PaymentSummaryResponse response = paymentService.verifyPaymentSignature(request);
        return ResponseEntity.ok(ApiResponse.ok("Payment signature verified successfully", response));
    }

    @GetMapping("/my-history")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<PaymentSummaryResponse>>> getMyPayments() {
        List<PaymentSummaryResponse> payments = paymentService.getUserPayments();
        return ResponseEntity.ok(ApiResponse.ok("User payment ledger retrieved", payments));
    }
}

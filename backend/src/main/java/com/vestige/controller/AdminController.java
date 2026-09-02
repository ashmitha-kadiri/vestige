package com.vestige.controller;

import com.vestige.dto.admin.AdminRecyclingDetailDTO;
import com.vestige.dto.admin.AdminRepairDetailDTO;
import com.vestige.dto.admin.AdminRewardAccountDTO;
import com.vestige.dto.admin.RegistrationActivityDTO;
import com.vestige.dto.admin.performance.AdminPerformanceDTO;
import com.vestige.dto.payment.PaymentAdminMetricsDTO;
import com.vestige.dto.request.AdminUserStatusRequest;
import com.vestige.dto.request.AdminVendorVerifyRequest;
import com.vestige.dto.response.ApiResponse;
import com.vestige.dto.response.UserSummaryResponse;
import com.vestige.dto.response.VendorSummaryResponse;
import com.vestige.model.AdminAction;
import com.vestige.service.AdminService;
import com.vestige.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final PaymentService paymentService;

    public AdminController(AdminService adminService, PaymentService paymentService) {
        this.adminService = adminService;
        this.paymentService = paymentService;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserSummaryResponse>>> getAllUsers() {
        List<UserSummaryResponse> users = adminService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.ok("User registry retrieved successfully", users));
    }

    @PatchMapping("/users/{id}/status")
    public ResponseEntity<ApiResponse<UserSummaryResponse>> updateUserStatus(
            @PathVariable UUID id,
            @Valid @RequestBody AdminUserStatusRequest request,
            HttpServletRequest servletRequest) {
        String clientIp = servletRequest.getRemoteAddr();
        UserSummaryResponse response = adminService.updateUserStatus(id, request, clientIp);
        return ResponseEntity.ok(ApiResponse.ok("User status updated successfully", response));
    }

    @GetMapping("/vendors")
    public ResponseEntity<ApiResponse<List<VendorSummaryResponse>>> getAllVendors() {
        List<VendorSummaryResponse> vendors = adminService.getAllVendors();
        return ResponseEntity.ok(ApiResponse.ok("Vendor registry retrieved successfully", vendors));
    }

    @GetMapping("/vendors/pending")
    public ResponseEntity<ApiResponse<List<VendorSummaryResponse>>> getPendingVendors() {
        List<VendorSummaryResponse> pending = adminService.getPendingVendors();
        return ResponseEntity.ok(ApiResponse.ok("Pending vendor applications retrieved", pending));
    }

    @PatchMapping("/vendors/{id}/verify")
    public ResponseEntity<ApiResponse<VendorSummaryResponse>> verifyVendor(
            @PathVariable UUID id,
            @Valid @RequestBody AdminVendorVerifyRequest request,
            HttpServletRequest servletRequest) {
        String clientIp = servletRequest.getRemoteAddr();
        VendorSummaryResponse response = adminService.verifyVendor(id, request, clientIp);
        return ResponseEntity.ok(ApiResponse.ok("Vendor verification status updated", response));
    }

    @GetMapping("/repairs")
    public ResponseEntity<ApiResponse<List<AdminRepairDetailDTO>>> getAllRepairs() {
        List<AdminRepairDetailDTO> repairs = adminService.getAllRepairs();
        return ResponseEntity.ok(ApiResponse.ok("Hardware restoration ledger retrieved", repairs));
    }

    @GetMapping("/recycling")
    public ResponseEntity<ApiResponse<List<AdminRecyclingDetailDTO>>> getAllRecycling() {
        List<AdminRecyclingDetailDTO> recycling = adminService.getAllRecycling();
        return ResponseEntity.ok(ApiResponse.ok("E-waste collection ledger retrieved", recycling));
    }

    @GetMapping("/rewards")
    public ResponseEntity<ApiResponse<List<AdminRewardAccountDTO>>> getAllRewards() {
        List<AdminRewardAccountDTO> rewards = adminService.getAllRewardAccounts();
        return ResponseEntity.ok(ApiResponse.ok("Circular rewards accounts retrieved", rewards));
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse<PaymentAdminMetricsDTO>> getPaymentMetrics() {
        PaymentAdminMetricsDTO metrics = paymentService.getAdminPaymentMetrics();
        return ResponseEntity.ok(ApiResponse.ok("Financial payment metrics retrieved", metrics));
    }

    @GetMapping("/registration-activity")
    public ResponseEntity<ApiResponse<RegistrationActivityDTO>> getRegistrationActivity(
            @RequestParam(defaultValue = "6m") String range) {
        RegistrationActivityDTO activity = adminService.getRegistrationActivity(range);
        return ResponseEntity.ok(ApiResponse.ok("Monthly user registration activity retrieved", activity));
    }

    @GetMapping("/performance")
    public ResponseEntity<ApiResponse<AdminPerformanceDTO>> getAdminPerformance(
            @RequestParam(defaultValue = "30d") String range) {
        AdminPerformanceDTO performance = adminService.getAdminPerformance(range);
        return ResponseEntity.ok(ApiResponse.ok("Platform performance analytics retrieved successfully", performance));
    }

    @GetMapping("/actions")
    public ResponseEntity<ApiResponse<List<AdminAction>>> getAdminActions() {
        List<AdminAction> actions = adminService.getAdminActions();
        return ResponseEntity.ok(ApiResponse.ok("Administrative audit trail retrieved", actions));
    }
}

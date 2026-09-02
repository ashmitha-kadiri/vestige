package com.vestige.controller;

import com.vestige.dto.analytics.*;
import com.vestige.dto.response.ApiResponse;
import com.vestige.exception.ForbiddenException;
import com.vestige.model.VendorProfile;
import com.vestige.repository.VendorProfileRepository;
import com.vestige.security.SecurityUtils;
import com.vestige.service.AnalyticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final VendorProfileRepository vendorProfileRepository;

    public AnalyticsController(AnalyticsService analyticsService, VendorProfileRepository vendorProfileRepository) {
        this.analyticsService = analyticsService;
        this.vendorProfileRepository = vendorProfileRepository;
    }

    // --------------------------------------------------------------------------
    // ADMIN ANALYTICS (Platform-wide, strictly ROLE_ADMIN)
    // --------------------------------------------------------------------------

    @GetMapping("/admin/overview")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdminOverviewDTO>> getAdminOverview(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        AdminOverviewDTO overview = analyticsService.getAdminOverview(from, to);
        return ResponseEntity.ok(ApiResponse.ok(overview));
    }

    @GetMapping("/admin/devices")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DeviceAnalyticsDTO>> getAdminDeviceAnalytics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        DeviceAnalyticsDTO analytics = analyticsService.getAdminDeviceAnalytics(from, to);
        return ResponseEntity.ok(ApiResponse.ok(analytics));
    }

    @GetMapping("/admin/repairs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RepairAnalyticsDTO>> getAdminRepairAnalytics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        RepairAnalyticsDTO analytics = analyticsService.getAdminRepairAnalytics(from, to);
        return ResponseEntity.ok(ApiResponse.ok(analytics));
    }

    @GetMapping("/admin/recycling")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RecyclingAnalyticsDTO>> getAdminRecyclingAnalytics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        RecyclingAnalyticsDTO analytics = analyticsService.getAdminRecyclingAnalytics(from, to);
        return ResponseEntity.ok(ApiResponse.ok(analytics));
    }

    @GetMapping("/admin/rewards")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RewardAnalyticsDTO>> getAdminRewardAnalytics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        RewardAnalyticsDTO analytics = analyticsService.getAdminRewardAnalytics(from, to);
        return ResponseEntity.ok(ApiResponse.ok(analytics));
    }

    @GetMapping("/admin/vendors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<VendorWorkloadSummaryDTO>>> getAdminVendorWorkload(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<VendorWorkloadSummaryDTO> workload = analyticsService.getAdminVendorWorkload(from, to);
        return ResponseEntity.ok(ApiResponse.ok(workload));
    }

    // --------------------------------------------------------------------------
    // VENDOR ANALYTICS (Scoped to own vendor_id derived from authenticated principal)
    // --------------------------------------------------------------------------

    @GetMapping("/vendor/overview")
    @PreAuthorize("hasRole('VENDOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<VendorAnalyticsDTO>> getVendorOverview(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        SecurityUtils.assertActive();
        UUID principalUserId = SecurityUtils.getCurrentUserId();
        VendorProfile profile = vendorProfileRepository.findByUserId(principalUserId)
                .orElseThrow(() -> new ForbiddenException("No vendor workshop profile linked to caller."));

        VendorAnalyticsDTO analytics = analyticsService.getVendorAnalytics(profile.getId(), from, to);
        return ResponseEntity.ok(ApiResponse.ok(analytics));
    }

    // --------------------------------------------------------------------------
    // USER ANALYTICS (Scoped to caller's user_id derived from authenticated principal)
    // --------------------------------------------------------------------------

    @GetMapping("/user/overview")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserAnalyticsDTO>> getUserOverview(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        SecurityUtils.assertActive();
        UUID principalUserId = SecurityUtils.getCurrentUserId();
        UserAnalyticsDTO analytics = analyticsService.getUserAnalytics(principalUserId, from, to);
        return ResponseEntity.ok(ApiResponse.ok(analytics));
    }
}

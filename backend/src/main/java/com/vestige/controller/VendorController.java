package com.vestige.controller;

import com.vestige.dto.response.ApiResponse;
import com.vestige.dto.response.VendorSummaryResponse;
import com.vestige.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VendorSummaryResponse>>> getVendors(
            @RequestParam(required = false) String serviceType
    ) {
        List<VendorSummaryResponse> responses = vendorService.getVendors(serviceType);
        return ResponseEntity.ok(ApiResponse.ok("Vendors retrieved successfully", responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorSummaryResponse>> getVendorById(@PathVariable UUID id) {
        VendorSummaryResponse response = vendorService.getVendorById(id);
        return ResponseEntity.ok(ApiResponse.ok("Vendor details retrieved", response));
    }
}

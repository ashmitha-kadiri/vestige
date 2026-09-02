package com.vestige.controller;

import com.vestige.dto.request.RecyclingRequestCreateDTO;
import com.vestige.dto.request.RecyclingStatusUpdateDTO;
import com.vestige.dto.response.ApiResponse;
import com.vestige.dto.response.RecyclingRequestResponse;
import com.vestige.model.enums.RecyclingStatusType;
import com.vestige.repository.UserRepository;
import com.vestige.service.RecyclingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recycling")
public class RecyclingController {

    private final RecyclingService recyclingService;
    private final com.vestige.repository.UserRepository userRepository;

    public RecyclingController(RecyclingService recyclingService, com.vestige.repository.UserRepository userRepository) {
        this.recyclingService = recyclingService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RecyclingRequestResponse>> createRequest(
            @Valid @RequestBody RecyclingRequestCreateDTO dto
    ) {
        RecyclingRequestResponse response = recyclingService.createRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("E-Waste recycling pickup request scheduled successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RecyclingRequestResponse>>> getAll(
            @RequestParam(required = false) RecyclingStatusType status
    ) {
        List<RecyclingRequestResponse> responses = recyclingService.getAll(status);
        return ResponseEntity.ok(ApiResponse.ok("Recycling requests retrieved", responses));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<RecyclingRequestResponse>>> getMyRequests(
            @RequestParam(required = false) UUID userId
    ) {
        UUID effectiveUserId = (userId != null) ? userId : com.vestige.security.SecurityUtils.getCurrentUserId();
        List<RecyclingRequestResponse> responses = recyclingService.getByUserId(effectiveUserId);
        return ResponseEntity.ok(ApiResponse.ok("User recycling requests retrieved", responses));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<RecyclingRequestResponse>>> getByUserId(@PathVariable UUID userId) {
        List<RecyclingRequestResponse> responses = recyclingService.getByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok("User recycling requests retrieved", responses));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ApiResponse<List<RecyclingRequestResponse>>> getByVendorId(@PathVariable UUID vendorId) {
        List<RecyclingRequestResponse> responses = recyclingService.getByVendorId(vendorId);
        return ResponseEntity.ok(ApiResponse.ok("Vendor recycling queue retrieved", responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecyclingRequestResponse>> getById(@PathVariable UUID id) {
        RecyclingRequestResponse response = recyclingService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("Recycling request details retrieved", response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<RecyclingRequestResponse>> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody RecyclingStatusUpdateDTO dto
    ) {
        RecyclingRequestResponse response = recyclingService.updateStatus(id, dto);
        String message = (dto.getStatus() == RecyclingStatusType.COMPLETED)
                ? String.format("Recycling request completed! Awarded %d circular reward points to user.", response.getPointsAwarded())
                : "Recycling request status updated to " + dto.getStatus();
        return ResponseEntity.ok(ApiResponse.ok(message, response));
    }
}

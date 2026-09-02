package com.vestige.controller;

import com.vestige.dto.request.RepairBookingCreateDTO;
import com.vestige.dto.request.RepairStatusUpdateDTO;
import com.vestige.dto.response.ApiResponse;
import com.vestige.dto.response.RepairBookingResponse;
import com.vestige.model.enums.BookingStatusType;
import com.vestige.security.SecurityUtils;
import com.vestige.service.RepairService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/repairs")
public class RepairController {

    private final RepairService repairService;

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RepairBookingResponse>> createBooking(@Valid @RequestBody RepairBookingCreateDTO dto) {
        RepairBookingResponse res = repairService.createBooking(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Repair booking created successfully in atelier queue", res));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RepairBookingResponse>> getById(@PathVariable UUID id) {
        RepairBookingResponse res = repairService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("Repair booking retrieved", res));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<RepairBookingResponse>>> getMyBookings() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        List<RepairBookingResponse> list = repairService.getByUserId(currentUserId);
        return ResponseEntity.ok(ApiResponse.ok("Patron repair bookings retrieved", list));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<RepairBookingResponse>>> getByUserId(@PathVariable UUID userId) {
        List<RepairBookingResponse> list = repairService.getByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok("User repair bookings retrieved", list));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<ApiResponse<List<RepairBookingResponse>>> getByVendorId(@PathVariable UUID vendorId) {
        List<RepairBookingResponse> list = repairService.getByVendorId(vendorId);
        return ResponseEntity.ok(ApiResponse.ok("Craftsman repair bookings retrieved", list));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RepairBookingResponse>>> getAll(
            @RequestParam(required = false) BookingStatusType status
    ) {
        List<RepairBookingResponse> list = repairService.getAll(status);
        return ResponseEntity.ok(ApiResponse.ok("Global repair bookings retrieved", list));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<RepairBookingResponse>> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody RepairStatusUpdateDTO dto
    ) {
        RepairBookingResponse res = repairService.updateStatus(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Repair status updated successfully", res));
    }
}

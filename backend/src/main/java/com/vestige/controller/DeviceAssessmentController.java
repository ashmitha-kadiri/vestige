package com.vestige.controller;

import com.vestige.dto.request.DeviceAssessmentRequest;
import com.vestige.dto.response.ApiResponse;
import com.vestige.dto.response.DeviceAssessmentResponse;
import com.vestige.service.DeviceSubmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/devices")
public class DeviceAssessmentController {

    private final DeviceSubmissionService deviceSubmissionService;

    public DeviceAssessmentController(DeviceSubmissionService deviceSubmissionService) {
        this.deviceSubmissionService = deviceSubmissionService;
    }

    @PostMapping("/assess")
    public ResponseEntity<ApiResponse<DeviceAssessmentResponse>> assessDevice(
            @Valid @RequestBody DeviceAssessmentRequest request
    ) {
        DeviceAssessmentResponse response = deviceSubmissionService.submitAssessment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Device assessment evaluated successfully by Decision Engine", response));
    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<DeviceAssessmentResponse>> submitDevice(
            @Valid @RequestBody DeviceAssessmentRequest request
    ) {
        return assessDevice(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DeviceAssessmentResponse>> getById(@PathVariable UUID id) {
        DeviceAssessmentResponse response = deviceSubmissionService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("Device assessment retrieved", response));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<DeviceAssessmentResponse>>> getByUserId(@PathVariable UUID userId) {
        List<DeviceAssessmentResponse> responses = deviceSubmissionService.getByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok("User device assessments retrieved", responses));
    }
}

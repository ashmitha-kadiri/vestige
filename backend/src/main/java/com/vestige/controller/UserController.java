package com.vestige.controller;

import com.vestige.dto.request.LanguageUpdateRequest;
import com.vestige.dto.response.ApiResponse;
import com.vestige.dto.response.UserSummaryResponse;
import com.vestige.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/me/language")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserSummaryResponse>> updateLanguage(
            @Valid @RequestBody LanguageUpdateRequest request) {
        UserSummaryResponse response = userService.updateLanguage(request);
        return ResponseEntity.ok(ApiResponse.ok("Language preference updated successfully", response));
    }
}

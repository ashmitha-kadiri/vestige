package com.vestige.controller;

import com.vestige.dto.request.RedeemRewardRequestDTO;
import com.vestige.dto.response.*;
import com.vestige.model.User;
import com.vestige.repository.UserRepository;
import com.vestige.service.RewardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;
    private final UserRepository userRepository;

    public RewardController(RewardService rewardService, UserRepository userRepository) {
        this.rewardService = rewardService;
        this.userRepository = userRepository;
    }

    @GetMapping("/account")
    public ResponseEntity<ApiResponse<RewardAccountResponse>> getAccount(
            @RequestParam(required = false) UUID userId
    ) {
        UUID effectiveId = (userId != null) ? userId : com.vestige.security.SecurityUtils.getCurrentUserId();
        RewardAccountResponse response = rewardService.getAccountResponse(effectiveId);
        return ResponseEntity.ok(ApiResponse.ok("Circular rewards account balance retrieved", response));
    }

    @GetMapping("/balance")
    public ResponseEntity<ApiResponse<RewardAccountResponse>> getBalance(
            @RequestParam(required = false) UUID userId
    ) {
        return getAccount(userId);
    }

    @GetMapping("/transactions")
    public ResponseEntity<ApiResponse<List<RewardTransactionResponse>>> getTransactions(
            @RequestParam(required = false) UUID userId
    ) {
        UUID effectiveId = (userId != null) ? userId : com.vestige.security.SecurityUtils.getCurrentUserId();
        List<RewardTransactionResponse> responses = rewardService.getTransactions(effectiveId);
        return ResponseEntity.ok(ApiResponse.ok("Rewards transaction ledger retrieved", responses));
    }

    @GetMapping("/catalog")
    public ResponseEntity<ApiResponse<List<RewardCatalogItemDTO>>> getCatalog() {
        List<RewardCatalogItemDTO> catalog = rewardService.getCatalog();
        return ResponseEntity.ok(ApiResponse.ok("Circular rewards catalog retrieved", catalog));
    }

    @PostMapping("/redeem")
    public ResponseEntity<ApiResponse<RedemptionResponse>> redeem(
            @Valid @RequestBody RedeemRewardRequestDTO dto
    ) {
        if (dto.getUserId() == null) {
            dto.setUserId(com.vestige.security.SecurityUtils.getCurrentUserId());
        }
        RedemptionResponse response = rewardService.redeemPoints(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Reward item successfully redeemed from circular balance", response));
    }

    @GetMapping("/redemptions")
    public ResponseEntity<ApiResponse<List<RedemptionResponse>>> getRedemptions(
            @RequestParam(required = false) UUID userId
    ) {
        UUID effectiveId = (userId != null) ? userId : com.vestige.security.SecurityUtils.getCurrentUserId();
        List<RedemptionResponse> responses = rewardService.getRedemptions(effectiveId);
        return ResponseEntity.ok(ApiResponse.ok("User redemptions retrieved", responses));
    }
}

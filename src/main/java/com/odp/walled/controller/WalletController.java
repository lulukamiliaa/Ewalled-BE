package com.odp.walled.controller;

import com.odp.walled.dto.common.ApiResponse;
import com.odp.walled.dto.wallet.WalletBalanceDto;
import com.odp.walled.dto.wallet.WalletResponseDto;
import com.odp.walled.model.User;
import com.odp.walled.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing wallet-related operations.
 */
@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    /**
     * Creates a new wallet for the authenticated user.
     *
     * @param currentUser the currently authenticated user
     * @return HTTP 201 Created with wallet details
     */
    @PostMapping
    public ResponseEntity<ApiResponse<WalletResponseDto>> createWalletForAuthenticatedUser(@AuthenticationPrincipal User currentUser) {

        WalletResponseDto wallet = walletService.createWallet(currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED.value(), "Wallet created successfully", wallet));
    }

    /**
     * Retrieves the current balance of the authenticated user's wallet.
     *
     * @param currentUser the currently authenticated user
     * @return HTTP 200 OK with wallet balance or HTTP 404 if not found
     */
    @GetMapping("/balance")
    public ResponseEntity<ApiResponse<WalletBalanceDto>> getAuthenticatedUserBalance(@AuthenticationPrincipal User currentUser) {

        WalletResponseDto wallet = walletService.getWalletByUserId(currentUser.getId());

        if (wallet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Wallet not found for the current user"));
        }

        WalletBalanceDto balanceDto = new WalletBalanceDto(wallet.getId(), wallet.getAccountNumber(), wallet.getBalance());

        return ResponseEntity.ok(ApiResponse.success("Balance fetched successfully", balanceDto));
    }
}

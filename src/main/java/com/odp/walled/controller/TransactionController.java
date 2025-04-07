package com.odp.walled.controller;

import com.odp.walled.dto.common.ApiResponse;
import com.odp.walled.dto.transaction.TopUpRequestDto;
import com.odp.walled.dto.transaction.TransactionRequestDto;
import com.odp.walled.dto.transaction.TransactionResponseDto;
import com.odp.walled.dto.transaction.TransferRequestDto;
import com.odp.walled.model.Transaction.TransactionType;
import com.odp.walled.model.User;
import com.odp.walled.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing wallet transactions including top-up, transfer,
 * and fetching transaction history.
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Process a generic transaction (if applicable).
     * Currently unused in favor of dedicated endpoints like top-up/transfer.
     *
     * @param request the transaction request payload
     * @return processed transaction response
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDto createTransaction(@Valid @RequestBody TransactionRequestDto request) {
        return transactionService.processTransaction(request);
    }

    /**
     * Get all transactions associated with a wallet, with optional filters.
     *
     * @param walletId        ID of the wallet
     * @param startDate       optional filter start date
     * @param endDate         optional filter end date
     * @param transactionType optional transaction type (TOP_UP, TRANSFER)
     * @return list of filtered transaction responses
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionResponseDto>>> getTransactionsByWallet(@RequestParam Long walletId, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, @RequestParam(required = false) TransactionType transactionType) {

        List<TransactionResponseDto> transactions = transactionService.getTransactionsByWallet(walletId, startDate, endDate, transactionType);
        return ResponseEntity.ok(ApiResponse.success("Transaction list fetched successfully.", transactions));
    }

    /**
     * Get all transactions related to the authenticated user (as sender or recipient).
     *
     * @param currentUser the currently authenticated user
     * @return transaction list involving the user
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<TransactionResponseDto>>> getMyTransactions(@AuthenticationPrincipal User currentUser) {
        List<TransactionResponseDto> transactions = transactionService.getAllTransactionsByUser(currentUser);
        return ResponseEntity.ok(ApiResponse.success("User transactions fetched successfully", transactions));
    }

    /**
     * Perform a top-up operation for the authenticated user.
     *
     * @param user    the authenticated user
     * @param request the top-up details (amount, optional description)
     * @return transaction response after top-up
     */
    @PostMapping("/top-up")
    public ResponseEntity<ApiResponse<TransactionResponseDto>> topUp(@AuthenticationPrincipal User user, @RequestBody TopUpRequestDto request) {

        TransactionResponseDto response = transactionService.topUp(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Top-up successful", response));
    }

    /**
     * Perform a transfer transaction from the authenticated user to another user.
     *
     * @param user    the authenticated user
     * @param request the transfer details (recipient, amount, optional description)
     * @return transaction response after transfer
     */
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponseDto>> transfer(@AuthenticationPrincipal User user, @RequestBody TransferRequestDto request) {

        TransactionResponseDto response = transactionService.transfer(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Transfer successful", response));
    }
}

package com.odp.walled.controller;

import com.odp.walled.dto.ApiResponse;
import com.odp.walled.dto.TransactionRequest;
import com.odp.walled.dto.TransactionResponse;
import com.odp.walled.model.Transaction.TransactionType;
import com.odp.walled.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionRequest request) {
        return transactionService.processTransaction(request);
    }

    // @GetMapping
    // public List<TransactionResponse> getTransactionsByWallet(@RequestParam Long walletId) {
    //     return transactionService.getTransactionsByWallet(walletId);
    // }

    // @GetMapping
    // public List<TransactionResponse> getTransactionsByWallet(@RequestParam Long walletId, LocalDateTime startDate, LocalDateTime endDate, TransactionType transactionType) {
    //     return transactionService.getTransactionsByWallet(walletId,startDate,endDate,transactionType);
    // }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactionsByWallet(
        @RequestParam Long walletId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) TransactionType transactionType) {

        List<TransactionResponse> transactions = transactionService.getTransactionsByWallet(walletId, startDate, endDate, transactionType);
        
        ApiResponse<List<TransactionResponse>> response = new ApiResponse<>(
            "success",
            HttpStatus.OK.value(),
            transactions
        );

        return ResponseEntity.ok(response);
    }
}
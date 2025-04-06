package com.odp.walled.controller;

import com.odp.walled.dto.common.ApiResponse;
import com.odp.walled.dto.transaction.TransactionRequestDto;
import com.odp.walled.dto.transaction.TransactionResponseDto;
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
    public TransactionResponseDto createTransaction(@Valid @RequestBody TransactionRequestDto request) {
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
    public ResponseEntity<ApiResponse<List<TransactionResponseDto>>> getTransactionsByWallet(
        @RequestParam Long walletId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) TransactionType transactionType) {

        List<TransactionResponseDto> transactions = transactionService.getTransactionsByWallet(walletId, startDate, endDate, transactionType);

        ApiResponse<List<TransactionResponseDto>> response = new ApiResponse<>(
            "success",
            HttpStatus.OK.value(),
            transactions
        );

        return ResponseEntity.ok(response);
    }
}
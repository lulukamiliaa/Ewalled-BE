package com.odp.walled.dto.transaction;

import com.odp.walled.model.Transaction.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) used for creating a new transaction request.
 * Can be used for top-up, transfer, or withdrawal depending on the transaction type.
 */
@Data
public class TransactionRequestDto {

    /**
     * ID of the wallet that initiates the transaction.
     * Cannot be {@code null}.
     */
    @NotNull(message = "Wallet ID is required")
    private Long walletId;

    /**
     * Type of the transaction (e.g., TOPUP, TRANSFER, WITHDRAW).
     * Cannot be {@code null}.
     */
    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    /**
     * Amount of the transaction. Must be greater than 0.01.
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    /**
     * Recipient's account number, required for transfer-type transactions.
     * Can be {@code null} for non-transfer transactions.
     */
    private String recipientAccountNumber;

    /**
     * Optional description or note for the transaction.
     */
    private String description;
}

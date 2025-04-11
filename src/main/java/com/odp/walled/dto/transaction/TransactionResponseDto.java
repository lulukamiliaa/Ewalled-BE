package com.odp.walled.dto.transaction;

import com.odp.walled.model.Transaction.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) used for returning transaction details
 * in API responses after a transaction is created or queried.
 */
@Data
public class TransactionResponseDto {

    /**
     * Unique identifier of the transaction.
     */
    private Long id;

    /**
     * ID of the wallet that initiated the transaction.
     */
    private Long walletId;

    /**
     * Type of the transaction (e.g., TOPUP, TRANSFER, WITHDRAW).
     */
    private TransactionType transactionType;

    /**
     * Amount of money involved in the transaction.
     */
    private BigDecimal amount;

    /**
     * ID of the recipient's wallet, applicable for transfer-type transactions.
     * Can be {@code null} for non-transfer transactions.
     */
    private Long recipientWalletId;

    /**
     * Timestamp when the transaction occurred.
     */
    private LocalDateTime transactionDate;

    /**
     * Optional description or note attached to the transaction.
     */
    private String description;
}

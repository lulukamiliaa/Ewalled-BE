package com.odp.walled.dto.transaction;

import com.odp.walled.model.Transaction.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {
    @NotNull
    private Long walletId;
    @NotNull
    private TransactionType transactionType;
    @DecimalMin("0.01")
    @NotNull
    private BigDecimal amount;
    private String recipientAccountNumber;
    private String description;
}
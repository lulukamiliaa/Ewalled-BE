// src/main/java/com/odp/walled/dto/TransactionDTO.java
package com.odp.walled.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private Long id;
    private Long walletId;
    private String transactionType;
    private BigDecimal amount;
    private Long recipientWalletId;
    private String description;
}
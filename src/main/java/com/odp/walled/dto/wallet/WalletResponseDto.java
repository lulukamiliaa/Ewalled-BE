package com.odp.walled.dto.wallet;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WalletResponseDto {
    private Long id;
    private Long userId;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
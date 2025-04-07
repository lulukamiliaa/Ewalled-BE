package com.odp.walled.dto.wallet;

import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.model.WalletType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WalletResponseDto {
    private Long id;
    private UserResponseDto user; // 💡 ini chaining DTO
    private String accountNumber;
    private BigDecimal balance;
    private WalletType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

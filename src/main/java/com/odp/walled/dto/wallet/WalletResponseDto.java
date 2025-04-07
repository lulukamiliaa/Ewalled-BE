package com.odp.walled.dto.wallet;

import com.odp.walled.model.WalletType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletResponseDto {
    private Long id;
    private Long userId;
    private String accountNumber;
    private BigDecimal balance;
    private WalletType type;
}

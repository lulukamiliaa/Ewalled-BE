// src/main/java/com/odp/walled/dto/WalletDTO.java
package com.odp.walled.dto.wallet;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Long userId;
}
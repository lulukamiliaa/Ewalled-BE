// src/main/java/com/odp/walled/dto/WalletDTO.java
package com.odp.walled.dto.wallet;

import com.odp.walled.model.WalletType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private WalletType type;
    private Long userId;

    public void setType(WalletType type) {
        this.type = type;
    }
}
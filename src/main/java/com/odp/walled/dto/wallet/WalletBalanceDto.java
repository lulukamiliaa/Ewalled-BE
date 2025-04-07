package com.odp.walled.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * A DTO representing the balance details of a wallet.
 * Used for scenarios where only basic wallet info is needed, such as balance checks.
 */
@Data
@AllArgsConstructor
public class WalletBalanceDto {

    /**
     * Unique identifier of the wallet.
     */
    private Long walletId;

    /**
     * The account number associated with the wallet.
     */
    private String accountNumber;

    /**
     * The current balance of the wallet.
     */
    private BigDecimal balance;
}

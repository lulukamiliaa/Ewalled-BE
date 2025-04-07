package com.odp.walled.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A lightweight representation of a Wallet entity,
 * used for summaries or nested embedding where full wallet details are not required.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinimalWalletDto {

    /**
     * Unique identifier of the wallet.
     */
    private Long id;

    /**
     * ID of the user who owns the wallet.
     */
    private Long userId;

    /**
     * Unique account number associated with the wallet.
     */
    private String accountNumber;
}

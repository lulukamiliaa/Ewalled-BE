package com.odp.walled.dto.user;

import com.odp.walled.dto.wallet.MinimalWalletDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a summary of a user and their wallet,
 * typically used for list views.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletSummaryDto {

    /**
     * Minimal user details (e.g., ID, name).
     */
    private MinimalUserDto user;

    /**
     * Minimal wallet details (e.g., ID, account number).
     */
    private MinimalWalletDto wallet;
}

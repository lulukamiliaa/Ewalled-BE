package com.odp.walled.dto.wallet;

import com.odp.walled.model.WalletType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) used to return wallet details in API responses.
 * Typically paired with user profile data or transaction overviews.
 */
@Data
public class WalletResponseDto {

    /**
     * Unique identifier of the wallet.
     */
    private Long id;

    /**
     * ID of the user who owns this wallet.
     */
    private Long userId;

    /**
     * Unique account number assigned to the wallet.
     */
    private String accountNumber;

    /**
     * Current balance of the wallet.
     */
    private BigDecimal balance;

    /**
     * Type of the wallet (e.g., PERSONAL, BUSINESS).
     */
    private WalletType type;
}

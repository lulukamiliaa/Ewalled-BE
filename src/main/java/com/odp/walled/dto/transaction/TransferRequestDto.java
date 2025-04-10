package com.odp.walled.dto.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO used to request a transfer transaction from the authenticated user's wallet
 * to another wallet in the system.
 */
@Data
public class TransferRequestDto {

    /**
     * ID of the recipient's wallet.
     */
    @NotNull(message = "Recipient wallet ID is required")
    private Long recipientWalletId;

    /**
     * Amount of money to transfer. Must be greater than 0.00.
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than zero")
    private BigDecimal amount;

    /**
     * Optional description for the transaction.
     */
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    /**
     * Optional flagging for the sedekah transaction.
     */
    private Boolean isSedekah;
}

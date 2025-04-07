package com.odp.walled.dto.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO representing a top-up request.
 * <p>
 * Contains the amount to top up and an optional description.
 */
@Data
public class TopUpRequestDto {

    /**
     * The amount to be topped up.
     * Must be a positive decimal number with up to 2 fractional digits.
     */
    @NotNull(message = "Amount must not be null.")
    @DecimalMin(value = "0.01", message = "Top-up amount must be greater than zero.")
    @Digits(integer = 12, fraction = 2, message = "Invalid amount format.")
    private BigDecimal amount;

    /**
     * Optional description of the top-up.
     */
    private String description;
}

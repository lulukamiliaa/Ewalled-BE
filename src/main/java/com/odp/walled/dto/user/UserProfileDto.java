package com.odp.walled.dto.user;

import com.odp.walled.dto.wallet.WalletResponseDto;
import lombok.Data;

/**
 * Data Transfer Object (DTO) that combines user and wallet details.
 * Typically used for returning a full user profile in a single response.
 */
@Data
public class UserProfileDto {

    /**
     * The user information, such as ID, name, email, etc.
     */
    private UserResponseDto user;

    /**
     * The wallet information associated with the user, including balance and wallet ID.
     */
    private WalletResponseDto wallet;
}

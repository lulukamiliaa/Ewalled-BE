package com.odp.walled.mapper;

import com.odp.walled.dto.user.MinimalUserDto;
import com.odp.walled.dto.user.UserWalletSummaryDto;
import com.odp.walled.dto.wallet.MinimalWalletDto;
import com.odp.walled.model.User;
import com.odp.walled.model.Wallet;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting {@link User} and {@link Wallet}
 * entities into their corresponding summary DTOs with only essential fields.
 */
@Component
public class UserWalletSummaryMapper {

    /**
     * Convert a User entity to a minimal user DTO.
     *
     * @param user the {@link User} entity
     * @return {@link MinimalUserDto} with essential user information
     */
    public MinimalUserDto toMinimalUser(User user) {
        MinimalUserDto dto = new MinimalUserDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        return dto;
    }

    /**
     * Convert a Wallet entity to a minimal wallet DTO.
     *
     * @param wallet the {@link Wallet} entity, can be null
     * @return {@link MinimalWalletDto} with essential wallet information, or null if wallet is null
     */
    public MinimalWalletDto toMinimalWallet(Wallet wallet) {
        if (wallet == null) return null;

        MinimalWalletDto dto = new MinimalWalletDto();
        dto.setId(wallet.getId());
        dto.setUserId(wallet.getUserId());  // assumed field from your model
        dto.setAccountNumber(wallet.getAccountNumber());
        return dto;
    }

    /**
     * Combine a user and their wallet (if exists) into a summary DTO.
     *
     * @param user   the {@link User} entity
     * @param wallet the {@link Wallet} entity (can be null)
     * @return {@link UserWalletSummaryDto} containing both minimal user and wallet info
     */
    public UserWalletSummaryDto toSummary(User user, Wallet wallet) {
        return new UserWalletSummaryDto(toMinimalUser(user), toMinimalWallet(wallet));
    }
}

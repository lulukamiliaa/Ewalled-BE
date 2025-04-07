package com.odp.walled.mapper;

import com.odp.walled.dto.user.MinimalUserDto;
import com.odp.walled.dto.user.UserWalletSummaryDto;
import com.odp.walled.dto.wallet.MinimalWalletDto;
import com.odp.walled.model.User;
import com.odp.walled.model.Wallet;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for transforming User and Wallet models into summary DTOs
 * that contain only essential fields.
 */
@Component
public class UserWalletSummaryMapper {

    /**
     * Maps a {@link User} entity to a minimal user DTO.
     *
     * @param user the user entity
     * @return a {@link MinimalUserDto} containing basic user information
     */
    public MinimalUserDto toMinimalUser(User user) {
        MinimalUserDto dto = new MinimalUserDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        return dto;
    }

    /**
     * Maps a {@link Wallet} entity to a minimal wallet DTO.
     *
     * @param wallet the wallet entity (nullable)
     * @return a {@link MinimalWalletDto} containing basic wallet information, or null if wallet is null
     */
    public MinimalWalletDto toMinimalWallet(Wallet wallet) {
        if (wallet == null) return null;
        MinimalWalletDto dto = new MinimalWalletDto();
        dto.setId(wallet.getId());
        dto.setUserId(wallet.getUser().getId());
        dto.setAccountNumber(wallet.getAccountNumber());
        return dto;
    }

    /**
     * Maps a {@link User} entity (and its wallet if exists) to a {@link UserWalletSummaryDto}.
     *
     * @param user the user entity
     * @return a summary DTO combining user and wallet info
     */
    public UserWalletSummaryDto toSummary(User user) {
        UserWalletSummaryDto dto = new UserWalletSummaryDto();
        dto.setUser(toMinimalUser(user));
        dto.setWallet(toMinimalWallet(user.getWallet()));
        return dto;
    }

    /**
     * Maps a list of {@link User} entities into a list of {@link UserWalletSummaryDto}.
     *
     * @param users the list of users
     * @return list of summary DTOs
     */
    public List<UserWalletSummaryDto> toSummaryList(List<User> users) {
        return users.stream().map(this::toSummary).toList();
    }
}

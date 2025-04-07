package com.odp.walled.mapper;

import com.odp.walled.dto.user.MinimalUserDto;
import com.odp.walled.dto.user.UserWalletSummaryDto;
import com.odp.walled.dto.wallet.WalletResponseDto;
import com.odp.walled.model.User;
import com.odp.walled.model.Wallet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserWalletSummaryMapper {

    public MinimalUserDto toMinimalUser(User user) {
        MinimalUserDto dto = new MinimalUserDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        return dto;
    }

    public WalletResponseDto toMinimalWallet(Wallet wallet) {
        if (wallet == null) return null;
        WalletResponseDto dto = new WalletResponseDto();
        dto.setId(wallet.getId());
        dto.setUserId(wallet.getUser().getId());
        dto.setAccountNumber(wallet.getAccountNumber());
        dto.setBalance(wallet.getBalance());
        dto.setType(wallet.getType());
        return dto;
    }

    public UserWalletSummaryDto toSummary(User user) {
        UserWalletSummaryDto dto = new UserWalletSummaryDto();
        dto.setUser(toMinimalUser(user));
        dto.setWallet(toMinimalWallet(user.getWallet()));
        return dto;
    }

    public List<UserWalletSummaryDto> toSummaryList(List<User> users) {
        return users.stream().map(this::toSummary).toList();
    }
}


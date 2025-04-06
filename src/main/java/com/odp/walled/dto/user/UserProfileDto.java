package com.odp.walled.dto.user;

import com.odp.walled.dto.wallet.WalletResponseDto;
import lombok.Data;

@Data
public class UserProfileDto {
    private UserResponseDto user;
    private WalletResponseDto wallet;
}

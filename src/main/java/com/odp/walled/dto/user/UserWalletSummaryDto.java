package com.odp.walled.dto.user;

import com.odp.walled.dto.wallet.WalletResponseDto;
import lombok.Data;

@Data
public class UserWalletSummaryDto {
    private MinimalUserDto user;
    private WalletResponseDto wallet;
}


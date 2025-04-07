package com.odp.walled.mapper;

import com.odp.walled.dto.wallet.WalletResponseDto;
import com.odp.walled.model.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface WalletMapper {
    WalletResponseDto toResponse(Wallet wallet);
}
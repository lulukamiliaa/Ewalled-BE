package com.odp.walled.mapper;

import com.odp.walled.dto.wallet.WalletResponseDto;
import com.odp.walled.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    @Mapping(source = "user.id", target = "userId")
    WalletResponseDto toResponse(Wallet wallet);
}
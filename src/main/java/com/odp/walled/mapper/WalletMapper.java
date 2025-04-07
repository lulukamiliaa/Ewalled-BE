package com.odp.walled.mapper;

import com.odp.walled.dto.wallet.WalletResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface WalletMapper {
    @Mapping(source = "user.id", target = "userId")
    WalletResponseDto toResponse(com.odp.walled.model.Wallet wallet);
}
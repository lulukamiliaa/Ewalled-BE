package com.odp.walled.mapper;

import com.odp.walled.dto.wallet.WalletResponseDto;
import com.odp.walled.model.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    /**
     * Maps a Wallet entity to a WalletResponseDto.
     *
     * @param wallet the wallet entity
     * @return the wallet response DTO
     */
    WalletResponseDto toResponse(Wallet wallet);
}

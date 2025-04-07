package com.odp.walled.mapper;

import com.odp.walled.dto.transaction.TransactionResponseDto;
import com.odp.walled.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting {@link Transaction} entities to {@link TransactionResponseDto}.
 * This is used to isolate model-layer logic from controller/service responses.
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    /**
     * Maps a {@link Transaction} entity to a {@link TransactionResponseDto}.
     *
     * @param transaction the entity to map
     * @return the mapped DTO with walletId and recipientWalletId extracted
     */
    @Mapping(source = "wallet.id", target = "walletId")
    @Mapping(source = "recipientWallet.id", target = "recipientWalletId")
    TransactionResponseDto toResponse(Transaction transaction);
}

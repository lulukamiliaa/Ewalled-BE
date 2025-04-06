package com.odp.walled.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.odp.walled.dto.transaction.TransactionResponseDto;
import com.odp.walled.model.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    //
//    @Mapping(source = "wallet.id", target = "walletId")
//    @Mapping(source = "recipientWallet.id",
//    target = "recipientWalletId")
    TransactionResponseDto toResponse(Transaction transaction);
}
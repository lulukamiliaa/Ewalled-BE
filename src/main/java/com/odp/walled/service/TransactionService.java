package com.odp.walled.service;

import com.odp.walled.dto.TransactionRequest;
import com.odp.walled.dto.TransactionResponse;
import com.odp.walled.exception.InsufficientBalanceException;
import com.odp.walled.exception.ResourceNotFound;
import com.odp.walled.mapper.TransactionMapper;
import com.odp.walled.model.Transaction;
import com.odp.walled.model.Transaction.TransactionType;
import com.odp.walled.model.Wallet;
import com.odp.walled.repository.TransactionRepository;
import com.odp.walled.repository.WalletRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponse processTransaction(TransactionRequest request) {
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> new ResourceNotFound("Wallet not found"));

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setTransactionType(request.getTransactionType());
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());

        if (request.getTransactionType() == TransactionType.TRANSFER) {
            Wallet recipient = walletRepository.findByAccountNumber(request.getRecipientAccountNumber())
                    .orElseThrow(() -> new ResourceNotFound("Recipient wallet not found"));
            
            if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
                throw new InsufficientBalanceException("Insufficient balance");
            }
            
            wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
            recipient.setBalance(recipient.getBalance().add(request.getAmount()));
            walletRepository.save(recipient);
            
            // Set the recipient wallet
            transaction.setRecipientWallet(recipient);
        } else {
            // TOP_UP
            wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        }

        walletRepository.save(wallet);
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

    // public List<TransactionResponse> getTransactionsByWallet(Long walletId) {
    //     List<Transaction> transactions = transactionRepository
    //             .findAllByWalletIdOrRecipientWalletId(walletId);
    //     return transactions.stream()
    //             .map(transactionMapper::toResponse)
    //             .toList();
    // }

    public List<TransactionResponse> getTransactionsByWallet(Long walletId, LocalDate startDate, LocalDate endDate, TransactionType transactionType) {
        // List<Transaction> transactions;

        // // If no filters exist, return all transactions for the wallet
        // if (startDate == null && endDate == null && transactionType == null) {
        //     transactions = transactionRepository.findAllByWalletIdOrRecipientWalletId(walletId);
        // } else {
        //     // Case 2: Some filters exist → Apply filtering dynamically
        // transactions = transactionRepository.findAllByWalletIdOrRecipientWalletIdAndFilters(walletId, startDate, endDate, transactionType);
        // }

        //new
        // List<Transaction> transactions = transactionRepository
        // .findAllByWalletIdOrRecipientWalletIdAndFilters(walletId, startDate, endDate, transactionType);

        //newer
        // Convert LocalDate to LocalDateTime
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        //newer
        List<Transaction> transactions = transactionRepository
                .findAllByWalletIdOrRecipientWalletId(walletId);

        // return transactions.stream()
        //         .map(transactionMapper::toResponse)
        //         .toList();

        //newer
        return transactions.stream()
            .filter(t -> startDate == null || !t.getTransactionDate().isBefore(startDateTime))
            .filter(t -> endDate == null || !t.getTransactionDate().isAfter(endDateTime))
            .filter(t -> transactionType == null || t.getTransactionType() == transactionType)
            .map(transactionMapper::toResponse)
            .toList();
    }
}
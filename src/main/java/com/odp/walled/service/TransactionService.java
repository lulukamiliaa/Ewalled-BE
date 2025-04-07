package com.odp.walled.service;

import com.odp.walled.dto.transaction.TopUpRequestDto;
import com.odp.walled.dto.transaction.TransactionRequestDto;
import com.odp.walled.dto.transaction.TransactionResponseDto;
import com.odp.walled.dto.transaction.TransferRequestDto;
import com.odp.walled.exception.InsufficientBalanceException;
import com.odp.walled.exception.ResourceNotFound;
import com.odp.walled.mapper.TransactionMapper;
import com.odp.walled.model.Transaction;
import com.odp.walled.model.Transaction.TransactionType;
import com.odp.walled.model.User;
import com.odp.walled.model.Wallet;
import com.odp.walled.repository.TransactionRepository;
import com.odp.walled.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    /**
     * Process a general transaction, either TOP_UP or TRANSFER.
     */
    @Transactional
    public TransactionResponseDto processTransaction(TransactionRequestDto request) {
        Wallet wallet = getWalletById(request.getWalletId());

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setTransactionType(request.getTransactionType());
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());

        if (request.getTransactionType() == TransactionType.TRANSFER) {
            Wallet recipient = getWalletByAccountNumber(request.getRecipientAccountNumber());
            validateSufficientBalance(wallet, request.getAmount());

            wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
            recipient.setBalance(recipient.getBalance().add(request.getAmount()));
            walletRepository.save(recipient);
            transaction.setRecipientWallet(recipient);
        } else {
            wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        }

        walletRepository.save(wallet);
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

    /**
     * Get all transactions for a wallet with optional filters.
     */
    public List<TransactionResponseDto> getTransactionsByWallet(Long walletId, LocalDate startDate, LocalDate endDate, TransactionType type) {
        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(LocalTime.MAX) : null;

        return transactionRepository.findAllByWalletIdOrRecipientWalletId(walletId).stream().filter(t -> start == null || !t.getTransactionDate().isBefore(start)).filter(t -> end == null || !t.getTransactionDate().isAfter(end)).filter(t -> type == null || t.getTransactionType() == type).map(transactionMapper::toResponse).toList();
    }

    /**
     * Top up balance to the authenticated user's wallet.
     */
    @Transactional
    public TransactionResponseDto topUp(User user, TopUpRequestDto request) {
        Wallet wallet = getWalletByUserId(user.getId());

        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        walletRepository.save(wallet);

        Transaction transaction = Transaction.builder().wallet(wallet).transactionType(TransactionType.TOP_UP).amount(request.getAmount()).description(request.getDescription()).transactionDate(LocalDateTime.now()).build();

        transactionRepository.save(transaction);
        return transactionMapper.toResponse(transaction);
    }

    /**
     * Transfer funds from the authenticated user's wallet to another wallet.
     */
    @Transactional
    public TransactionResponseDto transfer(User user, TransferRequestDto request) {
        Wallet sender = getWalletByUserId(user.getId());

        validateTransferRequest(sender, request);

        Wallet recipient = getWalletById(request.getRecipientWalletId());

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        recipient.setBalance(recipient.getBalance().add(request.getAmount()));

        walletRepository.save(sender);
        walletRepository.save(recipient);

        Transaction transaction = Transaction.builder().wallet(sender).recipientWallet(recipient).amount(request.getAmount()).transactionType(TransactionType.TRANSFER).description(request.getDescription()).transactionDate(LocalDateTime.now()).build();

        transactionRepository.save(transaction);
        return transactionMapper.toResponse(transaction);
    }

    /**
     * Get all transactions related to the authenticated user.
     */
    public List<TransactionResponseDto> getAllTransactionsByUser(User user) {
        Wallet wallet = getWalletByUserId(user.getId());

        return transactionRepository.findByWalletIdOrRecipientWalletId(wallet.getId(), wallet.getId()).stream().map(transactionMapper::toResponse).toList();
    }

    // ======================
    // Helper methods
    // ======================

    private Wallet getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFound("Wallet not found for user."));
    }

    private Wallet getWalletById(Long id) {
        return walletRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Wallet not found."));
    }

    private Wallet getWalletByAccountNumber(String accountNumber) {
        return walletRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new ResourceNotFound("Recipient wallet not found."));
    }

    private void validateSufficientBalance(Wallet wallet, BigDecimal amount) {
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }
    }

    private void validateTransferRequest(Wallet sender, TransferRequestDto request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for transfer.");
        }

        if (sender.getId().equals(request.getRecipientWalletId())) {
            throw new IllegalArgumentException("Cannot transfer to the same wallet.");
        }
    }
}

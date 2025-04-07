package com.odp.walled.service;

import com.odp.walled.dto.wallet.WalletResponseDto;
import com.odp.walled.exception.ResourceNotFound;
import com.odp.walled.exception.WalletAlreadyExistsException;
import com.odp.walled.mapper.WalletMapper;
import com.odp.walled.model.Wallet;
import com.odp.walled.model.WalletType;
import com.odp.walled.repository.UserRepository;
import com.odp.walled.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Service layer for handling wallet operations such as creation and retrieval.
 */
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final WalletMapper walletMapper;

    /**
     * Creates a wallet for the specified user if one does not already exist.
     *
     * @param userId the ID of the user
     * @return the newly created {@link WalletResponseDto}
     * @throws WalletAlreadyExistsException if the user already has a wallet
     * @throws ResourceNotFound             if the user does not exist
     */
    public WalletResponseDto createWallet(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFound("User not found");
        }

        if (walletRepository.findByUserId(userId).isPresent()) {
            throw new WalletAlreadyExistsException("User already has a wallet.");
        }

        Wallet newWallet = Wallet.builder().userId(userId).accountNumber(generateUniqueAccountNumber()).balance(BigDecimal.ZERO).type(WalletType.PERSONAL).build();

        walletRepository.save(newWallet);
        return walletMapper.toResponse(newWallet);
    }

    /**
     * Retrieves a wallet by its unique ID.
     *
     * @param walletId the ID of the wallet
     * @return the {@link WalletResponseDto}
     * @throws ResourceNotFound if the wallet does not exist
     */
    public WalletResponseDto getWalletById(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new ResourceNotFound("Wallet not found"));
        return walletMapper.toResponse(wallet);
    }

    /**
     * Retrieves a wallet using the associated user ID.
     *
     * @param userId the user ID
     * @return the {@link WalletResponseDto}
     * @throws ResourceNotFound if the wallet does not exist for the given user
     */
    public WalletResponseDto getWalletByUserId(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFound("Wallet not found for user ID: " + userId));
        return walletMapper.toResponse(wallet);
    }

    /**
     * Generates a unique 12-digit numeric account number.
     *
     * @return a unique account number string
     */
    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;

        do {
            accountNumber = String.format("%012d", Math.abs(random.nextLong()) % 1_000_000_000_000L);
        } while (walletRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}

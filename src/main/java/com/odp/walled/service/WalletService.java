package com.odp.walled.service;

import com.odp.walled.dto.wallet.WalletResponseDto;
import com.odp.walled.exception.ResourceNotFound;
import com.odp.walled.exception.WalletAlreadyExistsException;
import com.odp.walled.mapper.WalletMapper;
import com.odp.walled.model.User;
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
     */
    public WalletResponseDto createWallet(Long userId) {
        User user = findUserById(userId);

        if (user.getWallet() != null) {
            throw new WalletAlreadyExistsException("User already has a wallet.");
        }

        Wallet newWallet = Wallet.builder().user(user).accountNumber(generateUniqueAccountNumber()).balance(BigDecimal.ZERO).type(WalletType.PERSONAL).build();

        walletRepository.save(newWallet);
        return walletMapper.toResponse(newWallet);
    }

    /**
     * Retrieves a wallet by its unique ID.
     *
     * @param walletId the ID of the wallet
     * @return the {@link WalletResponseDto} of the wallet
     * @throws ResourceNotFound if the wallet does not exist
     */
    public WalletResponseDto getWalletById(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new ResourceNotFound("Wallet not found"));

        return walletMapper.toResponse(wallet);
    }

    /**
     * Generates a unique 12-digit numeric account number.
     *
     * @return a unique account number as a string
     */
    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;

        do {
            accountNumber = String.format("%012d", Math.abs(random.nextLong()) % 1_000_000_000_000L);
        } while (walletRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    /**
     * Fetches a user entity by ID, or throws {@link ResourceNotFound} if not found.
     *
     * @param userId the ID of the user
     * @return the {@link User} entity
     */
    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User not found"));
    }
}

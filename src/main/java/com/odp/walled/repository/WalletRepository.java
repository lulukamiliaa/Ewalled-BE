package com.odp.walled.repository;

import com.odp.walled.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing Wallet entities in the database.
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    /**
     * Find a wallet by its account number.
     *
     * @param accountNumber the unique account number
     * @return an Optional containing the wallet if found
     */
    Optional<Wallet> findByAccountNumber(String accountNumber);

    /**
     * Check if a wallet with the given account number exists.
     *
     * @param accountNumber the account number to check
     * @return true if a wallet with the given account number exists
     */
    boolean existsByAccountNumber(String accountNumber);

    /**
     * Find a wallet by the user ID.
     *
     * @param userId the ID of the user who owns the wallet
     * @return an Optional containing the wallet if found
     */
    Optional<Wallet> findByUserId(Long userId);
}

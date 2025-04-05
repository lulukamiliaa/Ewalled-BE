package com.odp.walled.repository;

import com.odp.walled.model.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.wallet.id = :walletId OR t.recipientWallet.id = :walletId")
    List<Transaction> findAllByWalletIdOrRecipientWalletId(@Param("walletId") Long walletId);

    @Query("SELECT t FROM Transaction t WHERE t.transactionDate >= :fromDate AND (t.wallet.id = :walletId OR t.recipientWallet.id = :walletId)")
    List<Transaction> findByWalletIdAndTransactionDateAfter(@Param("walletId") Long walletId, @Param("fromDate") LocalDateTime fromDate);

    // @Query("SELECT t FROM Transaction t " +
    //    "WHERE (t.wallet.id = :walletId OR t.recipientWallet.id = :walletId) " +
    //    "AND (:startDate IS NULL OR t.transactionDate >= :startDate) " +
    //    "AND (:endDate IS NULL OR t.transactionDate <= :endDate) " +
    //    "AND (:transactionType IS NULL OR t.transactionType = :transactionType)")
    // List<Transaction> findAllByWalletIdOrRecipientWalletIdAndFilters(
    //     @Param("walletId") Long walletId,
    //     @Param("startDate") @Nullable LocalDateTime startDate,
    //     @Param("endDate") @Nullable LocalDateTime endDate,
    //     @Param("transactionType") @Nullable TransactionType transactionType
    // );

}
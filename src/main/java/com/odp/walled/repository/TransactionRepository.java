package com.odp.walled.repository;

import com.odp.walled.model.Transaction;
import com.odp.walled.model.Transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find all transactions where the user is either the sender or recipient.
     *
     * @param walletId the wallet ID
     * @return list of transactions
     */
    @Query("SELECT t FROM Transaction t " + "WHERE t.wallet.id = :walletId OR t.recipientWallet.id = :walletId")
    List<Transaction> findAllByWalletIdOrRecipientWalletId(@Param("walletId") Long walletId);

    /**
     * Find all transactions after a specific date where the user is involved.
     *
     * @param walletId the wallet ID
     * @param fromDate the start date
     * @return filtered transactions
     */
    @Query("SELECT t FROM Transaction t " + "WHERE t.transactionDate >= :fromDate " + "AND (t.wallet.id = :walletId OR t.recipientWallet.id = :walletId)")
    List<Transaction> findByWalletIdAndTransactionDateAfter(@Param("walletId") Long walletId, @Param("fromDate") LocalDateTime fromDate);

    /**
     * Find all transactions where the user is sender or recipient.
     *
     * @param walletId          the sender wallet ID
     * @param recipientWalletId the recipient wallet ID
     * @return list of transactions
     */
    @Query("SELECT t FROM Transaction t " + "WHERE t.wallet.id = :walletId OR t.recipientWallet.id = :recipientWalletId")
    List<Transaction> findByWalletIdOrRecipientWalletId(@Param("walletId") Long walletId, @Param("recipientWalletId") Long recipientWalletId);

    /**
     * Optional filter query: range date and transaction type.
     *
     * @param walletId        wallet id of user
     * @param startDate       optional start date
     * @param endDate         optional end date
     * @param transactionType optional transaction type
     * @return filtered list of transactions
     */
    @Query("SELECT t FROM Transaction t " + "WHERE (t.wallet.id = :walletId OR t.recipientWallet.id = :walletId) " + "AND (:startDate IS NULL OR t.transactionDate >= :startDate) " + "AND (:endDate IS NULL OR t.transactionDate <= :endDate) " + "AND (:transactionType IS NULL OR t.transactionType = :transactionType)")
    List<Transaction> findWithFilters(@Param("walletId") Long walletId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("transactionType") TransactionType transactionType);
}

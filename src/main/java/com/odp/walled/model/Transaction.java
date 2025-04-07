package com.odp.walled.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a financial transaction within the system.
 * A transaction can either be a top-up or a transfer between wallets.
 */
@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    /**
     * Enum for the type of transaction.
     * - TOP_UP: Funds added to a wallet
     * - TRANSFER: Funds transferred from one wallet to another
     */
    public enum TransactionType {
        TOP_UP, TRANSFER
    }

    /**
     * Unique identifier for the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The wallet that initiated the transaction.
     * Required for both top-up and transfer.
     */
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    /**
     * Type of transaction (TOP_UP or TRANSFER).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    /**
     * The monetary amount involved in the transaction.
     */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    /**
     * The recipient wallet for transfer transactions.
     * Nullable in the case of TOP_UP transactions.
     */
    @ManyToOne
    @JoinColumn(name = "recipient_wallet_id")
    private Wallet recipientWallet;

    /**
     * Timestamp of when the transaction occurred.
     * Automatically set to the current time.
     */
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate = LocalDateTime.now();

    /**
     * Optional description for the transaction.
     */
    private String description;
}

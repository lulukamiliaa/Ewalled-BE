package com.odp.walled.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a digital wallet belonging to a user.
 * Stores account details, balance, and wallet type information.
 */
@Entity
@Table(name = "wallets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    /**
     * The unique identifier of the wallet.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user that owns this wallet.
     */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference
    private User user;

    /**
     * The unique account number associated with this wallet.
     */
    @NotBlank(message = "Account number must not be blank")
    @Size(max = 20, message = "Account number must be at most 20 characters")
    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    /**
     * The balance in the wallet. Cannot be negative.
     */
    @DecimalMin(value = "0.00", message = "Balance must not be negative")
    @Digits(integer = 12, fraction = 2, message = "Invalid balance format")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * The type of the wallet (e.g., PERSONAL, BUSINESS).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WalletType type = WalletType.PERSONAL;

    /**
     * The timestamp of when the wallet was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp of when the wallet was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

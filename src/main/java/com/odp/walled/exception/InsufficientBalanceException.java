package com.odp.walled.exception;

/**
 * Custom exception thrown when a transaction is attempted
 * but the wallet does not have sufficient balance.
 */
public class InsufficientBalanceException extends RuntimeException {

    /**
     * Constructs a new {@code InsufficientBalanceException} with the specified detail message.
     *
     * @param message the detail message explaining the insufficient balance error
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

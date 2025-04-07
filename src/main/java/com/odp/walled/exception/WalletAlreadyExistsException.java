package com.odp.walled.exception;

/**
 * Exception thrown when a user attempts to create a wallet
 * but already has one associated with their account.
 */
public class WalletAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new {@code WalletAlreadyExistsException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public WalletAlreadyExistsException(String message) {
        super(message);
    }
}

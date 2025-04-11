package com.odp.walled.exception;

/**
 * Custom exception thrown when a requested resource is not found.
 * Commonly used for missing entities such as users, wallets, or transactions.
 */
public class ResourceNotFound extends RuntimeException {

    /**
     * Constructs a new {@code ResourceNotFound} exception with the specified detail message.
     *
     * @param message the detail message explaining which resource was not found
     */
    public ResourceNotFound(String message) {
        super(message);
    }
}

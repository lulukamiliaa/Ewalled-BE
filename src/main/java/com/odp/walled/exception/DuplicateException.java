package com.odp.walled.exception;

/**
 * Custom exception thrown when a duplicate resource is encountered.
 * Typically used for conflicts such as existing email, phone number, or account number.
 */
public class DuplicateException extends RuntimeException {

    /**
     * Constructs a new {@code DuplicateException} with the specified detail message.
     *
     * @param message the detail message explaining the duplication conflict
     */
    public DuplicateException(String message) {
        super(message);
    }
}

package com.odp.walled.exception;

/**
 * Custom exception thrown when a PIN-related error occurs,
 * such as an incorrect PIN, missing PIN, or invalid format.
 */
public class PinException extends RuntimeException {

    /**
     * Constructs a new {@code PinException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the PIN error
     */
    public PinException(String message) {
        super(message);
    }
}

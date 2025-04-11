package com.odp.walled.exception;

/**
 * Custom exception thrown to indicate a bad request (HTTP 400).
 * Typically used when the client sends invalid or malformed data.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Constructs a new {@code BadRequestException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public BadRequestException(String message) {
        super(message);
    }
}

package com.odp.walled.dto.auth;

/**
 * Data Transfer Object (DTO) used for sending or verifying a user's transaction PIN.
 */
public class PinRequestDto {

    /**
     * The transaction PIN provided by the user.
     */
    private String pin;

    /**
     * Gets the transaction PIN.
     *
     * @return the PIN as a string
     */
    public String getPin() {
        return pin;
    }
}

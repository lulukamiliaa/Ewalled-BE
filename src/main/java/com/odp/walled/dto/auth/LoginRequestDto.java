package com.odp.walled.dto.auth;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) for user login requests.
 * Contains user credentials such as email and password.
 */
@Getter
public class LoginRequestDto {

    /**
     * The user's email address used for authentication.
     */
    private String email;

    /**
     * The user's password used for authentication.
     */
    private String password;

    /**
     * Sets the email for the login request.
     *
     * @param email the email address of the user
     * @return the updated {@code LoginRequestDto} instance
     */
    public LoginRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sets the password for the login request.
     *
     * @param password the password of the user
     * @return the updated {@code LoginRequestDto} instance
     */
    public LoginRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Returns a string representation of the login request object.
     * Warning: this includes the password field in plain text.
     *
     * @return a string representation of the login request
     */
    @Override
    public String toString() {
        return "LoginUserDto{" + "email='" + email + '\'' + '}';
    }
}

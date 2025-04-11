package com.odp.walled.dto.auth;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing the response after a successful login.
 * Contains JWT tokens used for authenticated access and session refresh.
 */
@Getter
public class LoginResponseDto {

    /**
     * JWT token used to authorize requests to protected resources.
     */
    private String accessToken;

    /**
     * JWT token used to renew the access token when it expires.
     */
    private String refreshToken;

    /**
     * Sets the access token for the response.
     *
     * @param accessToken the JWT access token
     * @return the updated {@code LoginResponseDto} instance
     */
    public LoginResponseDto setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Sets the refresh token for the response.
     *
     * @param refreshToken the JWT refresh token
     * @return the updated {@code LoginResponseDto} instance
     */
    public LoginResponseDto setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    /**
     * Returns a string representation of this object.
     * ⚠️ Avoid logging this in production as it may expose tokens.
     *
     * @return a string showing accessToken and refreshToken values
     */
    @Override
    public String toString() {
        return "LoginResponse{" + "accessToken='" + accessToken + '\'' + ", refreshToken='" + refreshToken + '\'' + '}';
    }
}

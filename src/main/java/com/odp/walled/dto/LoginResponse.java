package com.odp.walled.dto;

public class LoginResponse {
    // private String token;

    //new 
    private String accessToken;
    private String refreshToken;

    private long expiresIn;

    // public String getToken() {
    //     return token;
    // }

    // public LoginResponse setToken(String token) {
    //     this.token = token;
    //     return this;
    // }

    //new
    public String getAccessToken() {
        return accessToken;
    }

    public LoginResponse setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public LoginResponse setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    // @Override
    // public String toString() {
    //     return "LoginResponse{" +
    //             "token='" + token + '\'' +
    //             ", expiresIn=" + expiresIn +
    //             '}';
    // }

    //new
    @Override
    public String toString() {
        return "LoginResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}

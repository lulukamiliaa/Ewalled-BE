package com.odp.walled.dto.auth;

public class LoginRequestDto {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public LoginRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
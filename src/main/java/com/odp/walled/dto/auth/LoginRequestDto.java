package com.odp.walled.dto.auth;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;

    public LoginRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public LoginRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "LoginUserDto{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }
}
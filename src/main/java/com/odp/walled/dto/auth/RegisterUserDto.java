package com.odp.walled.dto.auth;

import lombok.Getter;

@Getter
public class RegisterUserDto {
    private String email;
    private String password;

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

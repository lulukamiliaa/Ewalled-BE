package com.odp.walled.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for user registration requests.
 * Includes required fields like email, password, full name, and phone number.
 */
@Getter
@Data
public class RegisterRequestDto {

    /**
     * User's email address. Must be valid and not blank.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    /**
     * User's password. Must be at least 6 characters and not blank.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    /**
     * User's full name. Cannot be blank.
     */
    @NotBlank(message = "Full name is required")
    private String fullName;

    /**
     * User's phone number. Cannot be blank.
     */
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    /**
     * Optional avatar URL for the user's profile image.
     */
    private String avatarUrl;

    /**
     * Sets the email for registration.
     *
     * @param email the email address
     * @return the updated {@code RegisterRequestDto} instance
     */
    public RegisterRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sets the password for registration.
     *
     * @param password the password
     * @return the updated {@code RegisterRequestDto} instance
     */
    public RegisterRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Returns a string representation of this object.
     * ⚠️ Avoid logging this in production to prevent exposing sensitive info.
     *
     * @return a string showing email and password values
     */
    @Override
    public String toString() {
        return "RegisterUserDto{email='" + email + "', password='[PROTECTED]'}";
    }
}

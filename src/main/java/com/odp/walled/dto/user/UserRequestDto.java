package com.odp.walled.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 1, max = 70)
    private String fullName;
    @NotBlank
    private String password;
    private String transactionPin;
    private String avatarUrl;
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,4}[-\\s.]?[0-9]{1,9}$", message = "Invalid phone number format")
    private String phoneNumber;
}

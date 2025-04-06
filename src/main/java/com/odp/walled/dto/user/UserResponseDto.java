package com.odp.walled.dto.user;

import com.odp.walled.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String phoneNumber;
}
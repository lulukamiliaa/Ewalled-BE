package com.odp.walled.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String fullname;
    private String avatarUrl;
    private String phoneNumber;
}
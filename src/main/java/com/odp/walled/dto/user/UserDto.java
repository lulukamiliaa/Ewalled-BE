// src/main/java/com/odp/walled/dto/UserDTO.java
package com.odp.walled.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private String password;
    private String avatarUrl;
}
package com.odp.walled.dto.user;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a user's public profile information.
 * Used for returning user data in API responses.
 */
@Data
public class UserResponseDto {

    /**
     * Unique identifier of the user.
     */
    private Long id;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Full name of the user.
     */
    private String fullName;

    /**
     * URL of the user's profile avatar image.
     */
    private String avatarUrl;

    /**
     * Phone number registered to the user.
     */
    private String phoneNumber;
}

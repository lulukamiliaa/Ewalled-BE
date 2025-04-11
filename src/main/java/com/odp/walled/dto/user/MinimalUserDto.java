package com.odp.walled.dto.user;

import lombok.Data;

/**
 * A minimal representation of a user, typically used for list or reference views.
 * Contains only essential identifying information.
 */
@Data
public class MinimalUserDto {

    /**
     * Unique identifier of the user.
     */
    private Long id;

    /**
     * Full name of the user.
     */
    private String fullName;
}

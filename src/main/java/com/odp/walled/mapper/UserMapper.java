package com.odp.walled.mapper;

import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.model.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting {@link User} entity into {@link UserResponseDto}.
 * Uses MapStruct to generate implementation at build time.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert a {@link User} entity to {@link UserResponseDto}.
     *
     * @param user the user entity
     * @return the user response DTO
     */
    UserResponseDto toResponse(User user);
}

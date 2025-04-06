package com.odp.walled.mapper;

import com.odp.walled.dto.user.UserRequestDto;
import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDto request);

    UserResponseDto toResponse(User user);
}
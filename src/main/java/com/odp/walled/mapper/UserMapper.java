package com.odp.walled.mapper;

import com.odp.walled.dto.user.UserRequestDto;
import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserRequestDto request);

    UserResponseDto toResponse(User user);
}
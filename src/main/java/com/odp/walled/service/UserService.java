package com.odp.walled.service;

import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.exception.ResourceNotFound;
import com.odp.walled.mapper.UserMapper;
import com.odp.walled.model.User;
import com.odp.walled.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for user-related business logic.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Retrieve a single user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return a mapped {@link UserResponseDto} of the user
     * @throws ResourceNotFound if the user is not found
     */
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("User not found"));
        return userMapper.toResponse(user);
    }

    /**
     * Get a list of all users in the system.
     *
     * @return a list of {@link User} entities
     */
    public List<User> allUsers() {
        return userRepository.findAll();
    }
}
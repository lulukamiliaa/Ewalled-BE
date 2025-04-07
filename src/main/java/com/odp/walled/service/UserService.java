package com.odp.walled.service;

import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.dto.user.UserWalletSummaryDto;
import com.odp.walled.exception.ResourceNotFound;
import com.odp.walled.mapper.UserMapper;
import com.odp.walled.mapper.UserWalletSummaryMapper;
import com.odp.walled.model.User;
import com.odp.walled.model.Wallet;
import com.odp.walled.repository.UserRepository;
import com.odp.walled.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer that handles user-related business logic such as fetching user data
 * and generating summary views for client-side consumption.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final UserMapper userMapper;
    private final UserWalletSummaryMapper userWalletSummaryMapper;

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param id the user's ID
     * @return a DTO representation of the user
     * @throws ResourceNotFound if the user does not exist
     */
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("User not found"));
        return userMapper.toResponse(user);
    }

    /**
     * Retrieve all users and their wallet summaries.
     *
     * @return list of user summaries including minimal wallet info
     */
    public List<UserWalletSummaryDto> allUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            Wallet wallet = walletRepository.findByUserId(user.getId()).orElse(null);
            return userWalletSummaryMapper.toSummary(user, wallet);
        }).toList();
    }
}

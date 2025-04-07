package com.odp.walled.controller;

import com.odp.walled.dto.common.ApiResponse;
import com.odp.walled.dto.user.UserProfileDto;
import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.dto.user.UserWalletSummaryDto;
import com.odp.walled.dto.wallet.WalletResponseDto;
import com.odp.walled.mapper.UserMapper;
import com.odp.walled.mapper.UserWalletSummaryMapper;
import com.odp.walled.mapper.WalletMapper;
import com.odp.walled.model.User;
import com.odp.walled.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final WalletMapper walletMapper;
    private final UserWalletSummaryMapper userWalletSummaryMapper;

    /**
     * Retrieve a user by their ID.
     *
     * @param id the ID of the user
     * @return the corresponding user as a UserResponse
     */
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Returns the currently authenticated user from the SecurityContext.
     *
     * @return a ResponseEntity containing the authenticated User
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        UserResponseDto userDto = userMapper.toResponse(currentUser);
        WalletResponseDto walletDto = walletMapper.toResponse(currentUser.getWallet());

        UserProfileDto profileDto = new UserProfileDto();
        profileDto.setUser(userDto);
        profileDto.setWallet(walletDto);

        return ResponseEntity.ok(ApiResponse.success("User profile fetched successfully", profileDto));
    }

    /**
     * Retrieve a list of all users in the system.
     *
     * @return a ResponseEntity containing a list of User objects
     */
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<UserWalletSummaryDto>>> allUsers() {
        List<User> users = userService.allUsers();
        List<UserWalletSummaryDto> summaries = userWalletSummaryMapper.toSummaryList(users);

        return ResponseEntity.ok(ApiResponse.success("List of users fetched successfully", summaries));
    }

    /**
     * POST endpoint to check if the current authenticated user has set a transaction PIN.
     *
     * @return true if PIN is set, false otherwise
     */
    @PostMapping("/has-pin")
    public ResponseEntity<ApiResponse<Boolean>> hasTransactionPin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        boolean hasPin = currentUser.getTransactionPin() != null;

        return ResponseEntity.ok(ApiResponse.success("Check PIN status success", hasPin));
    }

}

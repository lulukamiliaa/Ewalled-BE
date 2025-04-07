package com.odp.walled.controller;

import com.odp.walled.dto.common.ApiResponse;
import com.odp.walled.dto.user.UserProfileDto;
import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.dto.user.UserWalletSummaryDto;
import com.odp.walled.dto.wallet.WalletResponseDto;
import com.odp.walled.mapper.UserMapper;
import com.odp.walled.model.User;
import com.odp.walled.service.UserService;
import com.odp.walled.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final WalletService walletService;
    private final UserMapper userMapper;

    /**
     * Retrieve user by ID.
     *
     * @param id the ID of the user
     * @return ApiResponse containing UserResponseDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id) {
        UserResponseDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("User fetched successfully", userDto));
    }

    /**
     * Retrieve the authenticated user's profile.
     *
     * @param currentUser the authenticated user from security context
     * @return ApiResponse containing UserProfileDto
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> authenticatedUser(@AuthenticationPrincipal User currentUser) {
        UserResponseDto userDto = userMapper.toResponse(currentUser);
        WalletResponseDto walletDto = walletService.getWalletByUserId(currentUser.getId());

        UserProfileDto profileDto = new UserProfileDto();
        profileDto.setUser(userDto);
        profileDto.setWallet(walletDto);

        return ResponseEntity.ok(ApiResponse.success("User profile fetched successfully", profileDto));
    }

    /**
     * Retrieve all users with minimal profile and wallet summary.
     *
     * @return ApiResponse containing list of UserWalletSummaryDto
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserWalletSummaryDto>>> allUsers() {
        List<UserWalletSummaryDto> summaries = userService.allUsers();
        return ResponseEntity.ok(ApiResponse.success("List of users fetched successfully", summaries));
    }

    /**
     * Check if the authenticated user has set a transaction PIN.
     *
     * @param currentUser the authenticated user
     * @return ApiResponse with true if PIN is set, false otherwise
     */
    @PostMapping("/has-pin")
    public ResponseEntity<ApiResponse<Boolean>> hasTransactionPin(@AuthenticationPrincipal User currentUser) {
        boolean hasPin = currentUser.getTransactionPin() != null;
        return ResponseEntity.ok(ApiResponse.success("Transaction PIN status checked", hasPin));
    }
}

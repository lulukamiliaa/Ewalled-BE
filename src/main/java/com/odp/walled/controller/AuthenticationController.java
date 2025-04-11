package com.odp.walled.controller;

import com.odp.walled.dto.auth.LoginRequestDto;
import com.odp.walled.dto.auth.LoginResponseDto;
import com.odp.walled.dto.auth.PinRequestDto;
import com.odp.walled.dto.auth.RegisterRequestDto;
import com.odp.walled.dto.common.ApiResponse;
import com.odp.walled.model.User;
import com.odp.walled.service.AuthenticationService;
import com.odp.walled.service.JwtService;
import com.odp.walled.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that handles user authentication operations such as signup, login, logout,
 * token refresh, and PIN management.
 */
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    /**
     * Constructs a new AuthenticationController with required services.
     *
     * @param jwtService            the JWT service used for generating and validating tokens
     * @param authenticationService the authentication service handling business logic
     * @param refreshTokenService   the refresh token service for managing token lifecycles
     */
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Registers a new user and returns access and refresh tokens.
     *
     * @param registerRequestDto the registration request payload
     * @return a response containing JWT tokens upon successful registration
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<LoginResponseDto>> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        User user = authenticationService.signup(registerRequestDto);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenService.createRefreshToken(user.getEmail(), refreshToken, jwtService.getRefreshTokenExpiration());

        LoginResponseDto response = new LoginResponseDto().setAccessToken(accessToken).setRefreshToken(refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED.value(), "User registered and logged in successfully", response));
    }

    /**
     * Authenticates an existing user and returns JWT tokens.
     *
     * @param loginUserDto the login request payload
     * @return a response containing new JWT tokens upon successful login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> authenticate(@RequestBody LoginRequestDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String accessToken = jwtService.generateAccessToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        refreshTokenService.createRefreshToken(authenticatedUser.getEmail(), refreshToken, jwtService.getRefreshTokenExpiration());

        LoginResponseDto loginResponse = new LoginResponseDto().setAccessToken(accessToken).setRefreshToken(refreshToken);

        return ResponseEntity.ok(ApiResponse.success("Login successful", loginResponse));
    }

    /**
     * Logs out the currently authenticated user by clearing the refresh token and security context.
     *
     * @return a response indicating successful logout
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        refreshTokenService.deleteRefreshToken(email);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully.", null));
    }

    /**
     * Refreshes the access token using a valid refresh token.
     *
     * @param refreshToken the refresh token provided by the client
     * @return a response with a new access token if the refresh token is valid
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDto>> refresh(@RequestParam String refreshToken) {
        if (!refreshTokenService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(HttpStatus.FORBIDDEN.value(), "Invalid refresh token.", null));
        }

        String email = jwtService.extractUsername(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(authenticationService.loadUserByUsername(email));

        LoginResponseDto response = new LoginResponseDto().setAccessToken(newAccessToken).setRefreshToken(refreshToken);

        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", response));
    }

    /**
     * Sets a transaction PIN for the currently authenticated user.
     *
     * @param request the PIN request payload
     * @return a response indicating the PIN was successfully set
     */
    @PostMapping("/set-pin")
    public ResponseEntity<ApiResponse<Void>> setPin(@RequestBody PinRequestDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        authenticationService.setPinForUser(email, request.getPin());
        return ResponseEntity.ok(ApiResponse.success("PIN has been set successfully.", null));
    }

    /**
     * Verifies the provided transaction PIN against the stored PIN for the authenticated user.
     *
     * @param request the PIN verification request
     * @return a response indicating whether the PIN is valid
     */
    @PostMapping("/verify-pin")
    public ResponseEntity<ApiResponse<Void>> verify(@RequestBody PinRequestDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isPinValid = authenticationService.verifyPin(email, request.getPin());

        if (isPinValid) {
            return ResponseEntity.ok(ApiResponse.success("PIN has been verified successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Invalid PIN."));
        }
    }
}
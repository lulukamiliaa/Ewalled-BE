package com.odp.walled.controller;

import com.odp.walled.dto.auth.LoginRequestDto;
import com.odp.walled.dto.auth.LoginResponseDto;
import com.odp.walled.dto.auth.PinRequestDto;
import com.odp.walled.dto.auth.RegisterRequestDto;
import com.odp.walled.dto.common.ApiResponse;
import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.mapper.UserMapper;
import com.odp.walled.model.User;
import com.odp.walled.service.AuthenticationService;
import com.odp.walled.service.JwtService;
import com.odp.walled.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, RefreshTokenService refreshTokenService, UserMapper userMapper) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {

        User user = authenticationService.signup(registerRequestDto);
        UserResponseDto dto = userMapper.toResponse(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED.value(), "User registered successfully.", dto));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> authenticate(@RequestBody LoginRequestDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String accessToken = jwtService.generateAccessToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        refreshTokenService.createRefreshToken(authenticatedUser.getEmail(), refreshToken, jwtService.getRefreshTokenExpiration());

        LoginResponseDto loginResponse = new LoginResponseDto()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setExpiresIn(jwtService.getAccessTokenExpiration());

        return ResponseEntity.ok(ApiResponse.success("Login successful", loginResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        refreshTokenService.deleteRefreshToken(email);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully.", null));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDto>> refresh(@RequestParam String refreshToken) {
        if (!refreshTokenService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(HttpStatus.FORBIDDEN.value(), "Invalid refresh token.", null));
        }

        String email = jwtService.extractUsername(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(authenticationService.loadUserByUsername(email));

        LoginResponseDto response = new LoginResponseDto()
                .setAccessToken(newAccessToken)
                .setRefreshToken(refreshToken)
                .setExpiresIn(jwtService.getAccessTokenExpiration());

        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", response));
    }

    @PostMapping("/set-pin")
    public ResponseEntity<ApiResponse<Void>> setPin(@RequestBody PinRequestDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        authenticationService.setPinForUser(email, request.getPin());
        return ResponseEntity.ok(ApiResponse.success("PIN has been set successfully.", null));
    }

    @PostMapping("/verify-pin")
    public ResponseEntity<ApiResponse<Void>> verify(@RequestBody PinRequestDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isPinValid = authenticationService.verifyPin(email, request.getPin());

        if (isPinValid) {
            return ResponseEntity.ok(ApiResponse.success("PIN has been verified successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Invalid PIN."));
        }
    }
}

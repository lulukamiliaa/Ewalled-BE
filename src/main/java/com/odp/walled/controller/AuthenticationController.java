package com.odp.walled.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.odp.walled.dto.LoginResponse;
import com.odp.walled.dto.LoginUserDto;
import com.odp.walled.dto.RegisterUserDto;
import com.odp.walled.model.User;
import com.odp.walled.service.AuthenticationService;
import com.odp.walled.service.JwtService;
import com.odp.walled.service.RefreshTokenService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // String jwtToken = jwtService.generateToken(authenticatedUser);

        //new
        String accessToken = jwtService.generateAccessToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);

        // LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        //new 
        // ✅ Save refresh token to DB
        refreshTokenService.createRefreshToken(
            authenticatedUser.getEmail(),
            refreshToken,
            jwtService.getRefreshTokenExpiration()
        );

        //new
        LoginResponse loginResponse = new LoginResponse()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setExpiresIn(jwtService.getAccessTokenExpiration());

        return ResponseEntity.ok(loginResponse);
    }

    //TEMPORARY LOGOUT
    // @PostMapping("/logout")
    // public ResponseEntity<String> logout() {
    //     // Clear authentication context
    //     SecurityContextHolder.clearContext();
    //     return ResponseEntity.ok("Logout successful");
    // }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String email) {
        refreshTokenService.deleteRefreshToken(email);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestParam String refreshToken) {
        if (!refreshTokenService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(403).body(null);
        }

        String email = jwtService.extractUsername(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(authenticationService.loadUserByUsername(email));

        LoginResponse response = new LoginResponse()
                .setAccessToken(newAccessToken)
                .setRefreshToken(refreshToken)
                .setExpiresIn(jwtService.getAccessTokenExpiration());

        return ResponseEntity.ok(response);
    }

}

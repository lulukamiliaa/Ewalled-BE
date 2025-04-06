package com.odp.walled.controller;

import com.odp.walled.dto.user.UserRequestDto;
import com.odp.walled.dto.user.UserResponseDto;
import com.odp.walled.model.User;
import com.odp.walled.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Create a new user.
     *
     * @param request the user data in the request body
     * @return the created user as a UserResponse
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto request) {
        return userService.createUser(request);
    }

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
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    /**
     * Retrieve a list of all users in the system.
     *
     * @return a ResponseEntity containing a list of User objects
     */
    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
}

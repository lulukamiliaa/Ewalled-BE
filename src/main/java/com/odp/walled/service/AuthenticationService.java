package com.odp.walled.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odp.walled.dto.LoginUserDto;
import com.odp.walled.dto.RegisterUserDto;
import com.odp.walled.exception.DuplicateException;
import com.odp.walled.model.User;
import com.odp.walled.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        // Cek apakah email sudah terdaftar
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new DuplicateException("Email already exists. Please log in.");
        }

        if (input.getPhoneNumber() != null && userRepository.existsByPhoneNumber(input.getPhoneNumber())) {
                throw new DuplicateException("Phone number already in use");
        }

        User user = new User()
                .setEmail(input.getEmail())
                .setFullName(input.getFullName())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setPhoneNumber(input.getPhoneNumber());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}

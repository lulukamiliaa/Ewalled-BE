package com.odp.walled.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odp.walled.dto.auth.LoginRequestDto;
import com.odp.walled.dto.auth.RegisterUserDto;
import com.odp.walled.exception.DuplicateException;
import com.odp.walled.model.User;
import com.odp.walled.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
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

    public User authenticate(LoginRequestDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    public void setPinForUser(String email, String pin) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getTransactionPin() != null) {
            throw new IllegalStateException("PIN has already been set");
        }

        String hashedPin = passwordEncoder.encode(pin);
        user.setTransactionPin(hashedPin);
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

}

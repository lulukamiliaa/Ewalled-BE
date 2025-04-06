package com.odp.walled.service;

import com.odp.walled.dto.auth.LoginRequestDto;
import com.odp.walled.dto.auth.RegisterUserDto;
import com.odp.walled.exception.DuplicateException;
import com.odp.walled.exception.PinException;
import com.odp.walled.model.User;
import com.odp.walled.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     */
    @Transactional
    public User signup(RegisterUserDto input) {
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new DuplicateException("Email already exists. Please log in.");
        }

        if (input.getPhoneNumber() != null && userRepository.existsByPhoneNumber(input.getPhoneNumber())) {
            throw new DuplicateException("Phone number already in use.");
        }

        User newUser = User.builder()
                .email(input.getEmail())
                .fullName(input.getFullName())
                .password(passwordEncoder.encode(input.getPassword()))
                .phoneNumber(input.getPhoneNumber())
                .build();

        return userRepository.save(newUser);
    }

    /**
     * Authenticate user login
     */
    public User authenticate(LoginRequestDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }

    /**
     * Set a 6-digit PIN for the user, one-time only
     */
    @Transactional
    public void setPinForUser(String email, String pin) {
        if (!pin.matches("\\d{6}")) {
            throw new PinException("PIN must be exactly 6 digits.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        if (user.getTransactionPin() != null) {
            throw new PinException("PIN has already been set.");
        }

        user.setTransactionPin(passwordEncoder.encode(pin));
        userRepository.save(user);
    }

    public boolean verifyPin(String email, String pin) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        // Compare input PIN (plaintext) with hashed transactionPin
        return passwordEncoder.matches(pin, user.getTransactionPin());
    }

    /**
     * Load user by email for Spring Security
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}

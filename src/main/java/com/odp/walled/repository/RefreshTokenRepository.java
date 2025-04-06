package com.odp.walled.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odp.walled.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByEmail(String email);
}


package com.odp.walled.service;

import org.springframework.stereotype.Service;

import com.odp.walled.model.RefreshToken;
import com.odp.walled.repository.RefreshTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String email, String token, long expiryTimeMillis) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setEmail(email);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + expiryTimeMillis));

        return refreshTokenRepository.save(refreshToken);
    }

    public boolean validateRefreshToken(String token) {
        Optional<RefreshToken> storedToken = refreshTokenRepository.findByToken(token);
        return storedToken.isPresent() && storedToken.get().getExpiryDate().after(new Date());
    }

    @Transactional
    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteByEmail(email);
    }
}


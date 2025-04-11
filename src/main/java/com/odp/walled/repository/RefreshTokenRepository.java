package com.odp.walled.repository;

import com.odp.walled.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link RefreshToken} entities.
 * Provides methods to perform CRUD operations and custom queries on refresh tokens.
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Finds a refresh token entity by its token string.
     *
     * @param token the refresh token string
     * @return an {@code Optional} containing the matching {@link RefreshToken}, if found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Deletes a refresh token associated with a specific email address.
     *
     * @param email the email address whose refresh token should be deleted
     */
    void deleteByEmail(String email);
}

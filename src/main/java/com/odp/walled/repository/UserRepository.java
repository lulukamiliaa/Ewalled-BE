package com.odp.walled.repository;

import com.odp.walled.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing {@link User} entities.
 * Extends {@link JpaRepository} to provide basic CRUD operations and custom queries.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email to search for
     * @return an {@code Optional} containing the user if found, or empty if not
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user exists with the given phone number.
     *
     * @param phoneNumber the phone number to check
     * @return {@code true} if a user with the given phone number exists, {@code false} otherwise
     */
    boolean existsByPhoneNumber(String phoneNumber);
}

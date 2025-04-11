package com.odp.walled.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity representing a refresh token used to obtain new access tokens
 * after the original access token has expired.
 */
@Getter
@Entity
public class RefreshToken {

    /**
     * Primary key identifier of the refresh token record.
     * -- GETTER --
     * Gets the ID of the refresh token.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique refresh token string.
     * -- GETTER --
     *  Gets the token string.
     * <p>
     *
     * -- SETTER --
     *  Sets the token string.
     *
     */
    @Setter
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * The email of the user to whom this refresh token is associated.
     * -- GETTER --
     *  Gets the email associated with this token.
     *

     */
    @Column(nullable = false)
    private String email;

    /**
     * Expiry date of the refresh token.
     * -- GETTER --
     *  Gets the expiration date of the token.
     * <p>
     *
     * -- SETTER --
     *  Sets the expiration date of the token.
     *
     */
    @Setter
    @Column(nullable = false)
    private Date expiryDate;

    // Getters & Setters

    /**
     * Sets the ID of the refresh token.
     *
     * @param id the ID to set
     * @return the current {@code RefreshToken} instance
     */
    public RefreshToken setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the email associated with this token.
     *
     * @param email the email to set
     * @return the current {@code RefreshToken} instance
     */
    public RefreshToken setEmail(String email) {
        this.email = email;
        return this;
    }

}

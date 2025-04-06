package com.odp.walled.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Date expiryDate;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public RefreshToken setId(Long id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public RefreshToken setToken(String token) {
        this.token = token;
        return this;
    }
    
    public String getEmail() {
        return email;
    }

    public RefreshToken setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public RefreshToken setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }
}


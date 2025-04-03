package com.odp.walled.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 70)
    private String fullName;

    @Column(nullable = false)
    private String password;

    @Column(name = "transaction_pin", length = 6)
    private String transactionPin;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @OneToOne(mappedBy = "user")
    private Wallet wallet; // Each User has exactly one Wallet

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // returns the user’s roles list; it is helpful to manage permissions
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        //returns the email adress because it's the unique info about the user
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    
    //transactionPin
    public String getTransactionPin() {
        return transactionPin;
    }

    public User setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
        return this;
    }

    //avatarUrl
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public User setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    //phoneNumber
    public String getPhoneNumber() {
        return avatarUrl;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", transactionPin='" + transactionPin + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
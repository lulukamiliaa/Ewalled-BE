package com.odp.walled.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Represents a user in the system.
 * Implements Spring Security's UserDetails for authentication support.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    /**
     * Primary key for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email used for login (also acts as the username).
     */
    @Column(nullable = false, unique = true, length = 255)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * Full name of the user.
     */
    @Column(name = "full_name", nullable = false, length = 70)
    @NotBlank(message = "Full name is required")
    private String fullName;

    /**
     * Encrypted login password.
     */
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    /**
     * Hashed transaction PIN (optional).
     */
    @Column(name = "transaction_pin", length = 100)
    private String transactionPin;

    /**
     * Optional avatar image URL.
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * Optional phone number.
     */
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    /**
     * Return email as username for Spring Security.
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * Return encrypted password for Spring Security.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Return granted authorities (empty by default).
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Can be extended for role-based auth
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // can be customized
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // can be customized
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // can be customized
    }

    @Override
    public boolean isEnabled() {
        return true; // can be customized
    }

    /**
     * String representation for debugging/logging.
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", fullName='" + fullName + '\'' + ", email='" + email + '\'' + ", avatarUrl='" + avatarUrl + '\'' + ", phoneNumber='" + phoneNumber + '\'' + '}';
    }
}

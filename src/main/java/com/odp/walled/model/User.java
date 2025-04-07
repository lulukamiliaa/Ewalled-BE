package com.odp.walled.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    @Email
    @NotBlank
    private String email;

    @Column(name = "full_name", nullable = false, length = 70)
    @NotBlank
    private String fullName;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Column(name = "transaction_pin", length = 100)
    private String transactionPin;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Wallet wallet;

    public String getEmail() {
        return this.email;
    }
    // Spring Security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // customize if roles needed
    }

    @Override
    public String getUsername() {
        return this.email; // email as username
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // change if account expiration applied
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // change if lock mechanism applied
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // change if credential expiry applied
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

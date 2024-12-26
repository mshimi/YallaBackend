package org.example.yalla_api.common.entities.auth;

import jakarta.persistence.*;
import lombok.Data;
import org.example.yalla_api.common.enums.Role;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@Data
 public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password; // Nullable for OAuth users

    @Enumerated(EnumType.STRING)
    private Role role ;

    private Boolean isAccountNonExpired = true;


    private Boolean isAccountNonLocked = true;

    private Boolean isCredentialsNonExpired = true;

    private Boolean isEnabled = true;

    private String name;

    private String firstName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_" + getRole().name());
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired; // Adjust as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked; // Adjust as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired; // Adjust as needed
    }

    @Override
    public boolean isEnabled() {
        return isEnabled; // Adjust as needed
    }
}




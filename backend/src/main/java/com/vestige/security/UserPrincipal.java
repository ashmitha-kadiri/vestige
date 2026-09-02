package com.vestige.security;

import com.vestige.model.User;
import com.vestige.model.enums.PreferredLanguage;
import com.vestige.model.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserPrincipal implements UserDetails {

    private final UUID id;
    private final String email;
    private final String fullName;
    private final UserRole role;
    private final PreferredLanguage preferredLanguage;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UUID id, String email, String fullName, UserRole role,
                         PreferredLanguage preferredLanguage, boolean active) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.preferredLanguage = preferredLanguage;
        this.active = active;
        this.authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );
    }

    public static UserPrincipal fromEntity(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                user.getPreferredLanguage(),
                user.isActive()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public PreferredLanguage getPreferredLanguage() {
        return preferredLanguage;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return ""; // Not stored or used in Spring Boot; Supabase Auth manages credentials
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}

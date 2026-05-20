package org.example.logistics_crm.security;

import org.example.logistics_crm.entity.user.User;
import org.example.logistics_crm.entity.user.UserRole;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NullMarked
public class SecurityUser implements UserDetails {
    private final User user;

    public SecurityUser(User user) {
        Objects.requireNonNull(user, "User must not be null");
        this.user = user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        if (user.getUserRole() == null) {
            return false;
        }
        return user.getUserRole() != UserRole.PENDING;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getUserRole() == null) {
            return List.of();
        }
        String userRoleName = "ROLE_" + user.getUserRole().name().toUpperCase();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRoleName);
        return List.of(authority);
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}

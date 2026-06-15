package org.example.logistics_crm.security;

import org.example.logistics_crm.entity.client.Client;
import org.example.logistics_crm.entity.client.ClientStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NullMarked
public class SecurityClient implements UserDetails {

    private final Client client;
    private final Long id;

    public SecurityClient(Client client) {
        Objects.requireNonNull(client, "Client must not be null");
        this.client = client;
        this.id = client.getId();
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
        if(client.getStatus() == null){
            return false;
        }
        return client.getStatus() == ClientStatus.ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String clientRole = "ROLE_CLIENT";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(clientRole);
        return List.of(authority);
    }

    @Override
    public @Nullable String getPassword() {
        return client.getPassword();
    }

    @Override
    public String getUsername() {
        return client.getEmail();
    }

    public Long getId() {
        return id;
    }
}

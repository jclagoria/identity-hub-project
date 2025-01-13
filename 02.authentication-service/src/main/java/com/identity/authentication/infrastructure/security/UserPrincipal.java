package com.identity.authentication.infrastructure.security;

import com.identity.authentication.domain.model.UserAuthentication;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserPrincipal implements UserDetails {

    private final long id;
    private final String email;
    private final String passwordHash;
    private final boolean emailVerified;

    public UserPrincipal(UserAuthentication userAuthentication) {
        this.id = userAuthentication.getId();
        this.email = userAuthentication.getEmail();
        this.passwordHash = userAuthentication.getPasswordHash();
        this.emailVerified = userAuthentication.isEmailVerified();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retornar roles. Simplificado con ROLE_USER
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {return true;}
    @Override
    public boolean isAccountNonLocked() {return true;}
    @Override
    public boolean isCredentialsNonExpired() {return true;}
    @Override
    public boolean isEnabled() {return emailVerified;}

}

package com.avenuer.faxi.authentication.config;

import com.avenuer.faxi.authentication.models.AuthCredential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class FaxiUserDetails implements UserDetails {

    private AuthCredential userCredential;

    FaxiUserDetails(AuthCredential cred) {
        this.userCredential = cred;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userCredential.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return userCredential.getEmail();
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
}

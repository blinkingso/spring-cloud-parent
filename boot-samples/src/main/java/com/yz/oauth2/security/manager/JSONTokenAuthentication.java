package com.yz.oauth2.security.manager;

import lombok.Data;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class JSONTokenAuthentication implements Authentication {

    private Token token;
    private final List<GrantedAuthority> authorities;
    private final AuthenticatedPrincipal authenticatedPrincipal;
    private Boolean isAuthenticated;

    public JSONTokenAuthentication(Token token, List<GrantedAuthority> authorities, AuthenticatedPrincipal authenticatedPrincipal) {
        this.token = token;
        this.authorities = authorities;
        this.authenticatedPrincipal = authenticatedPrincipal;
        this.isAuthenticated = true;
    }

    public JSONTokenAuthentication(Token token, List<GrantedAuthority> authorities, AuthenticatedPrincipal authenticatedPrincipal, Boolean isAuthenticated) {
        this.token = token;
        this.authorities = authorities;
        this.authenticatedPrincipal = authenticatedPrincipal;
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return this.token.getToken();
    }

    @Override
    public Object getDetails() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.authenticatedPrincipal;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.token.getToken();
    }

    @Data
    static class Token {
        private String token;
        private Long timeExpired;
        private String signature;
        private String encrypt;
    }
}

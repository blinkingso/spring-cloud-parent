package com.yz.oauth2.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义Authentication
 *
 * @author andrew
 * @date 2020-10-14
 */
public class YzRemoteOriginAuthentication extends UsernamePasswordAuthenticationToken {

    public YzRemoteOriginAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public YzRemoteOriginAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}

package com.yz.pojo.authentication;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 校验token
 *
 * @author andrew
 * @date 2020-10-16
 */
public class TokenAuthentication extends UsernamePasswordAuthentication {

    public TokenAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public TokenAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}

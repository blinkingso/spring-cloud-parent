package com.yz.pojo.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

/**
 * @author andrew
 * @date 2020-10-15
 */
public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

    public UsernamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials, AuthorityUtils.commaSeparatedStringToAuthorityList("read,write,delete"));
    }

    public UsernamePasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}

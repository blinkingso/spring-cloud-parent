package com.yz.pojo.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * @author andrew
 * @date 2020-10-15
 */
public class OtpAuthentication extends UsernamePasswordAuthenticationToken {

    public OtpAuthentication(Object principal, Object credentials) {
        super(principal, credentials, AuthorityUtils.commaSeparatedStringToAuthorityList("read,write"));
    }
}

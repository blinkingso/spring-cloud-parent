package com.yz.authentication.provider;

import com.yz.authentication.UsernamePasswordAuthentication;
import com.yz.proxy.AuthenticationServerProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author andrew
 * @date 2020-11-04
 */
@Slf4j
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationServerProxy proxy;

    public UsernamePasswordAuthenticationProvider(AuthenticationServerProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (this.proxy.sendAuth(username, password)) {
            return new UsernamePasswordAuthentication(username, password);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // check UsernamePasswordAuthentication class type
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
    }
}

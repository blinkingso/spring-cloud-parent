package com.yz.authentication.provider;

import com.yz.authentication.OtpAuthentication;
import com.yz.proxy.AuthenticationServerProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author andrew
 * @date 2020-11-04
 */
@Slf4j
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationServerProxy proxy;

    public OtpAuthenticationProvider(AuthenticationServerProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String code = authentication.getCredentials().toString();
        if (this.proxy.sendOTP(username, code)) {
            return new OtpAuthentication(username, code);
        } else {
            throw new BadCredentialsException("Bad credentials .");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.isAssignableFrom(authentication);
    }
}

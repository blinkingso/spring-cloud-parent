package com.yz.providers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * custom {@link AuthenticationProvider} implementation for {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
 *
 * @author andrew
 * @date 2020-10-28
 */
@Component("customAuthenticationProvider")
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            log.info("now start to authenticate user : " + username);
            final UserDetails user = this.userDetailsService.loadUserByUsername(username);
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
            }

            throw new BadCredentialsException("invalid password");
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            log.error("auth error", e);
            throw new BadCredentialsException("bad username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}

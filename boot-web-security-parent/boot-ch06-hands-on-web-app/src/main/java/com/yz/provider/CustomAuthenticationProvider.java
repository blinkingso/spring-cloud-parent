package com.yz.provider;

import com.yz.pojo.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        SecurityUser userDetails = (SecurityUser) this.userDetailsService.loadUserByUsername(username);

        try {
            return checkPassword(password, userDetails);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            log.error("auth error", e);
            throw new BadCredentialsException("Bad Credentials", e);
        }
    }

    private Authentication checkPassword(String password, SecurityUser userDetails) {
        var encryptedPassword = "";
        switch (userDetails.getUser().getAlgorithm()) {
            case BCRYPT:
                encryptedPassword = "{bcrypt}" + userDetails.getPassword();
                break;
            case SCRYPT:
                encryptedPassword = "{scrypt}" + userDetails.getPassword();
                break;
        }

        if (this.passwordEncoder.matches(password, encryptedPassword)) {
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Bad Password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

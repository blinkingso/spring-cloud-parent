package com.yz.security.providers;

import com.yz.pojo.authentication.TokenAuthentication;
import com.yz.security.cache.TokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author andrew
 * @date 2020-10-16
 */
@Slf4j
public class YzTokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenCache tokenCache;

    public YzTokenAuthenticationProvider(TokenCache tokenCache) {
        this.tokenCache = tokenCache;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("执行token校验的AP");
        String token = authentication.getName();
        if (!this.tokenCache.contains(token)) {
            throw new BadCredentialsException("token 不合法哟...");
        }
        return new TokenAuthentication(token, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}

package com.yz.oauth2.security.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author andrew
 * @date 2020-10-14
 */
@Slf4j
@Component
public class YzAuthenticationManager implements AuthenticationManager {

    public static class YzAuthenticationManagerBuilder {

        private List<AuthenticationProvider> providers = new ArrayList<>();

        private YzAuthenticationManagerBuilder() {
        }

        public static YzAuthenticationManagerBuilder createBuilder() {
            return new YzAuthenticationManagerBuilder();
        }

        public YzAuthenticationManagerBuilder authenticationProviders(List<AuthenticationProvider> authenticationProviders) {
            this.providers.addAll(authenticationProviders);
            return this;
        }

        public YzAuthenticationManagerBuilder authenticationProvider(AuthenticationProvider authenticationProvider) {
            this.providers.add(authenticationProvider);
            return this;
        }

        public YzAuthenticationManager builder() {
            YzAuthenticationManager authenticationManager = new YzAuthenticationManager();
            authenticationManager.providers = this.providers;
            return authenticationManager;
        }
    }

    /**
     * 验证器
     */
    private List<AuthenticationProvider> providers;

    private YzAuthenticationManager() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (this.providers == null) {
            throw new InternalAuthenticationServiceException("no authentication providers provided");
        }

        // 找到列表中第一个支持的provider
        Optional<AuthenticationProvider> opt = this.providers.stream().filter(provider -> provider.supports(authentication.getClass())).findFirst();
        return opt.map(authenticationProvider -> authenticationProvider.authenticate(authentication)).orElse(null);
    }
}

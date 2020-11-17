package com.yz.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author andrew
 * @date 2020-11-17
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    private final JwtProperties jwtProperties;

    public ProjectConfig(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // configure oauth2 users
        final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager
                .createUser(User.withUsername("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("ADMIN")
                        .build());
        return userDetailsManager;
    }

    @Configuration
    @ConditionalOnProperty(prefix = "jwt", name = "is-symmetric", havingValue = "true")
    public static class SymmetricConfiguration {
        private final JwtProperties jwtProperties;

        public SymmetricConfiguration(JwtProperties jwtProperties) {
            this.jwtProperties = jwtProperties;
        }

        @Bean
        @Primary
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(jwtProperties.getSigningKey());
            return jwtAccessTokenConverter;
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "jwt", name = "is-symmetric", havingValue = "false")
    public static class AsymmetricConfiguration {
        private final JwtProperties jwtProperties;

        public AsymmetricConfiguration(JwtProperties jwtProperties) {
            this.jwtProperties = jwtProperties;
        }

        @Bean
        @Primary
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            // 非对称加密算法RSA
            final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(jwtProperties.getPrivateKey()), jwtProperties.getKeyPass()
                    .toCharArray());
            jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(jwtProperties.getAlias()));
            return jwtAccessTokenConverter;
        }
    }

    @Bean
    @Primary
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

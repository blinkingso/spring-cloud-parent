package com.yz.config;

import com.yz.config.enhance.token.EnhanceTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.crypto.spec.SecretKeySpec;

/**
 * @author andrew
 * @date 2020-11-17
 */
@Configuration
public class ProjectConfig {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    @ConditionalOnProperty(value = "jwt.type", havingValue = "symmetric")
    public JwtAccessTokenConverter jwtAccessTokenConverter(@Value("${jwt.key}") String signingKey) {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signingKey);
        return jwtAccessTokenConverter;
    }

    @Bean(name = "jwtAccessTokenConverter")
    @Primary
    @ConditionalOnProperty(value = "jwt.type", havingValue = "asymmetric")
    public JwtAccessTokenConverter jwtAccessTokenConverter2(@Value("${jwt.public-key}") String publicKey) {
        // converter custom map
        final JwtAccessTokenConverter jwtAccessTokenConverter = new EnhanceTokenConverter();
        jwtAccessTokenConverter.setVerifierKey(publicKey);
        return jwtAccessTokenConverter;
    }

    @Bean
    @Primary
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtDecoder jwtDecoder(@Value("${jwt.key}") String signingKey) {
        byte[] key = signingKey.getBytes();
        final SecretKeySpec aes = new SecretKeySpec(key, 0, key.length, "AES");
        return NimbusJwtDecoder.withSecretKey(aes).build();
    }

}

package com.yz.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;

/**
 * Auto {@link org.springframework.security.crypto.password.PasswordEncoder} Definition
 */
@ConditionalOnMissingBean(PasswordEncoder.class)
@Configuration
public class PasswordEncodingAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(value = {"user.password.delegating",
            "user.password.default"}, havingValue = "true", matchIfMissing = true)
    static class DefaultConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(value = {"user.password.delegating"}, havingValue = "true")
    static class CustomConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            var encoders = new HashMap<String, PasswordEncoder>();
            encoders.put("noop", NoOpPasswordEncoder.getInstance());
            encoders.put("bcrypt", new BCryptPasswordEncoder());
            encoders.put("scrypt", new SCryptPasswordEncoder());
            return new DelegatingPasswordEncoder("bcrypt", encoders);
        }
    }
}

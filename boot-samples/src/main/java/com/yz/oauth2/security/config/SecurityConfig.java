package com.yz.oauth2.security.config;

import com.yz.oauth2.security.InMemoryUserDetailsServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author andrew
 * @date 2020-10-14
 */
@Configuration
public class SecurityConfig {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public InMemoryUserDetailsServices userDetailsServices() {
        return new InMemoryUserDetailsServices(passwordEncoder());
    }
}

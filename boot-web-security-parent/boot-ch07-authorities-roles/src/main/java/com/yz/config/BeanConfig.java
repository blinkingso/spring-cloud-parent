package com.yz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 定义Beans
 *
 * @author andrew
 * @date 2020-10-30
 */
@Configuration
public class BeanConfig {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        final var userDetailsManager = new InMemoryUserDetailsManager();
        var admin = User.withUsername("admin").password(passwordEncoder.encode("admin"))
                .authorities("READ", "WRITE").build();
        var guest = User.withUsername("guest").password(passwordEncoder.encode("guest"))
                .authorities("READ").build();
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(guest);
        return userDetailsManager;
    }
}

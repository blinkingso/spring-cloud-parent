package com.yz.config;

import com.yz.enums.AuthoritiesDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    // UserDetailsService using ROLE_ to configure authorities
    @Bean
    @Primary
    public UserDetailsService userDetailsService0(PasswordEncoder passwordEncoder) {
        final var manager = new InMemoryUserDetailsManager();
        var admin = User.withUsername("admin").password(passwordEncoder.encode("admin"))
                // all authorities
                .roles("ADMIN").build();
        var guest = User.withUsername("guest").password(passwordEncoder.encode("guest"))
                .roles("GUEST").build();
        var guest2 = User.withUsername("guest2").password(passwordEncoder.encode("guest2"))
                .roles("GUEST").build();
        manager.createUser(admin);
        manager.createUser(guest);
        manager.createUser(guest2);
        return manager;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        final var userDetailsManager = new InMemoryUserDetailsManager();
        var admin = User.withUsername("admin").password(passwordEncoder.encode("admin"))
                // all authorities
                .authorities(AuthoritiesDefinition.allToString()).build();
        var guest = User.withUsername("guest").password(passwordEncoder.encode("guest"))
                .authorities(AuthoritiesDefinition.toString(AuthoritiesDefinition.READ, AuthoritiesDefinition.WRITE)).build();
        var guest2 = User.withUsername("guest2").password(passwordEncoder.encode("guest2"))
                .authorities(AuthoritiesDefinition.toString(AuthoritiesDefinition.DELETE)).build();
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(guest);
        userDetailsManager.createUser(guest2);
        return userDetailsManager;
    }
}

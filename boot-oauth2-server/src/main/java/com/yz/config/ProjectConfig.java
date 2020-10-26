package com.yz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * project configuration for beans
 *
 * @author andrew
 * @date 2020-10-22
 */
@Configuration(proxyBeanMethods = false)
public class ProjectConfig {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService(@Autowired PasswordEncoder passwordEncoder) {
        final InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin")).authorities("read", "write")
                .roles("ADMIN").build();
        UserDetails guest = User.withUsername("guest").password(passwordEncoder.encode("guest")).authorities("read")
                .roles("GUEST").build();
        manager.createUser(admin);
        manager.createUser(guest);
        return manager;
    }
}

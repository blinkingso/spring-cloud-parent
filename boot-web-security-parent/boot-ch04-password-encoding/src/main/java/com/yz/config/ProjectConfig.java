package com.yz.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * global configuration for the current project
 *
 * @author andrew
 * @date 2020-10-27
 */
@Configuration
@Slf4j
@Import({PasswordEncodingAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class})
public class ProjectConfig {

//    @Bean
//    @Primary
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}

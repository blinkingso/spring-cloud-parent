package com.yz.config;

import com.yz.userdetails.DummyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author andrew
 * @date 2020-10-27
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
public class UserDetailsServiceAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean(PasswordEncoder.class)
    @ConditionalOnClass(PasswordEncoder.class)
    @ConditionalOnProperty(value = "user.details.service.type", havingValue = "memory", matchIfMissing = true)
    static class MemoryConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
            // 定义一个admin的用户
            log.info("initializing a in-memory type UserDetailsService instance");
            return new InMemoryUserDetailsManager(List.of(new DummyUser(passwordEncoder)));
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean({DataSource.class})
    @ConditionalOnClass({DataSource.class})
    @ConditionalOnProperty(value = "user.details.service.type", havingValue = "jdbc")
    static class JdbcConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public UserDetailsService userDetailsService(DataSource dataSource) {
            log.info("initializing a jdbc type UserDetailsService instance");
            return new JdbcUserDetailsManager(dataSource);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean({DataSource.class})
    @ConditionalOnClass({DataSource.class})
    @ConditionalOnProperty(value = "user.details.service.type", havingValue = "custom")
    static class CustomConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public UserDetailsService userDetailsService(DataSource dataSource) {
            log.info("initializing a custom jdbc type UserDetailsService instance");
            return new JdbcUserDetailsManager(dataSource);
        }
    }
}

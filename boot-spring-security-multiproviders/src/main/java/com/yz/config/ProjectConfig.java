package com.yz.config;

import com.yz.dao.UserRepository;
import com.yz.service.DefaultUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * @author andrew
 * @date 2020-10-15
 */
@Configuration
@ComponentScan("com.yz")
public class ProjectConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create(this.getClass().getClassLoader())
                .type(DriverManagerDataSource.class)
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/yz?useSSL=false")
                .username("root")
                .password("123456")
                .build();
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService(@Autowired UserRepository userRepository) {
        return new DefaultUserDetailsService(userRepository);
    }
}

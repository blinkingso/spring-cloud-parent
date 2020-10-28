package com.yz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Implementing authentication
 * This chapter covers:
 * 1. Implementing authentication logic using a custom {@link org.springframework.security.authentication.AuthenticationProvider}
 * 2. Using the HTTP Basic and form-based login authentication methods
 * 3. Understanding and managing the {@link org.springframework.security.core.context.SecurityContext} component
 *
 * @author andrew
 * @date 2020-10-28
 */
@SpringBootApplication
public class AppCh05_8080 {
    public static void main(String[] args) {
        SpringApplication.run(AppCh05_8080.class, args);
    }
}

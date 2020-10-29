package com.yz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This chapter covers:
 * <p>
 * 1. Applying authentication in a hands-on example
 * 2. Defining the user with the {@link org.springframework.security.core.userdetails.UserDetails} Interface
 * 3. Defining a custom {@link org.springframework.security.core.userdetails.UserDetailsService}
 * 4. Using a provided implementation of {@link org.springframework.security.crypto.password.PasswordEncoder}
 * 5. Defining your authentication logic by implementing and {@link org.springframework.security.authentication.AuthenticationProvider}
 * 6. Setting the form-login authentication method
 *
 * @author andrew
 * @date 2020-10-29
 */
@SpringBootApplication
public class AppCh06_8080 {
    public static void main(String[] args) {
        SpringApplication.run(AppCh06_8080.class, args);
    }
}

package com.yz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * chapter3 covers:
 * 1. Describing a user with the {@link org.springframework.security.core.userdetails.UserDetails} interface
 * 2. Using the {@link org.springframework.security.core.userdetails.UserDetailsService} in the authentication flow
 * 3. Creating a custom implementation of {@link org.springframework.security.core.userdetails.UserDetailsService}
 * 4. Creating a custom implementation of {@link org.springframework.security.provisioning.UserDetailsManager}
 * 5. Using the {@link org.springframework.security.provisioning.JdbcUserDetailsManager} in the authentication flow
 * <p>
 * <p>
 * ===>UserDetails, which describes the user for Spring Security.
 * ===> GrantedAuthority, which allows us to define actions that the user can
 * execute.
 * ===> UserDetailsManager, which extends the UserDetailsService contract.
 * Beyond the inherited behavior, it also describes actions like creating a user and modifying or deleting a user’s password.
 * <p>
 * <p>
 * Auth Flow:
 * <p>
 * SpringSecurity接收请求处理过程
 * User Request -> Authentication Filter {SecurityContext} -> AuthenticationManager delegate to ===>
 * AuthenticationProvider  ---select first AuthenticationProvider that match the Authentication---> authenticate
 * with(UserDetailsService and PasswordEncoder)
 */
@SpringBootApplication
public class AppCh03_8080 {

    public static void main(String[] args) {
        SpringApplication.run(AppCh03_8080.class, args);
    }
}

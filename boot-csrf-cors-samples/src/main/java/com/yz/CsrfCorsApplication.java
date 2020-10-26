package com.yz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author andrew
 * @date 2020-10-21
 */
@SpringBootApplication
// enable transactional
@EnableTransactionManagement
//@EnableWebSecurity
//@EnableWebMvc
public class CsrfCorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsrfCorsApplication.class, args);
    }
}

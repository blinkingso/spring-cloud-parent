package com.yz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author andrew
 * @date 2020-10-21
 */
@SpringBootApplication
// enable transactional
@EnableTransactionManagement
public class CsrfCorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsrfCorsApplication.class, args);
    }
}

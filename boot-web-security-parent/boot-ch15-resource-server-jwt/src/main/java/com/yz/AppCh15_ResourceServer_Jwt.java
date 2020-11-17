package com.yz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author andrew
 * @date 2020-11-17
 */
@SpringBootApplication
@EnableResourceServer
public class AppCh15_ResourceServer_Jwt {

    public static void main(String[] args) {
        SpringApplication.run(AppCh15_ResourceServer_Jwt.class, args);
    }
}

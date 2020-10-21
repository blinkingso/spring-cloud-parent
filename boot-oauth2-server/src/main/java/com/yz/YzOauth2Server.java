package com.yz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Oauth2 认证服务器
 *
 * @author andrew
 * @date 2020-10-21
 */
@SpringBootApplication
public class YzOauth2Server {

    public static void main(String[] args) {
        SpringApplication.run(YzOauth2Server.class, args);
    }
}

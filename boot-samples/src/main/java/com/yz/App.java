package com.yz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author andrew
 * @date 2020-10-10
 */
@SpringBootApplication(scanBasePackages = {"com.yz.oauth2"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

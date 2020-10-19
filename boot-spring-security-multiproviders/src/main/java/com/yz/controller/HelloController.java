package com.yz.controller;

import com.yz.pojo.authentication.OtpAuthentication;
import com.yz.pojo.authentication.TokenAuthentication;
import com.yz.pojo.authentication.UsernamePasswordAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrew
 * @date 2020-10-15
 */
@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    @Async
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("保存的Authentication为: " + authentication.getClass().getName());
        if (authentication instanceof TokenAuthentication) {
            String token = authentication.getName();
            log.info("controller中解析到的token为: " + token);
        } else if (authentication instanceof UsernamePasswordAuthentication) {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            log.info("controller中解析到的username为: " + username + ", password为: " + password);
        } else if (authentication instanceof OtpAuthentication) {
            String otp = authentication.getCredentials().toString();
            String username = authentication.getName();
            log.info("controller中解析到的username为: " + username + ", otp is: " + otp);
        }
        return "hello ---> " + authentication.getName();
    }
}

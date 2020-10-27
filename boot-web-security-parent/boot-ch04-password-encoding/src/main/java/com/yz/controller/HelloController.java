package com.yz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public String getHello() {
        printAuthName();
        return "getHello";
    }

    @PostMapping("hello")
    public String postHello() {
        printAuthName();
        return "postHello";
    }

    private void printAuthName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            log.info("auth = " + auth.getName());
        }
    }
}

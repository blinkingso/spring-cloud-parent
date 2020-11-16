package com.yz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrew
 * @date 2020-11-16
 */
@RestController
@RequestMapping
public class HelloController {

    @GetMapping("/hello")
    public String simpleHello() {
        return "simple get hello";
    }
}

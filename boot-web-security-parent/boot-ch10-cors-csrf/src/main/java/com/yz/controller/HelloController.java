package com.yz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrew
 * @date 2020-11-02
 */
@RestController
@RequestMapping
public class HelloController {

    @GetMapping(value = {"/hello"})
    public String getHello() {
        return "get Hello";
    }

    @PostMapping(value = {"/hello"})
    public String postHello() {
        return "post Hello";
    }
}

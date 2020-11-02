package com.yz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrew
 * @date 2020-11-01
 */
@RestController
@RequestMapping
public class CrossSiteRequestForgeryController {

    @GetMapping("/a")
    public String getA() {
        return "get A works";
    }

    @PostMapping("/a")
    public String postA() {
        return "post A works";
    }

    @GetMapping("/a/b")
    public String getAb() {
        return "get A and B works";
    }

    @PostMapping("/a/b")
    public String postAb() {
        return "post A and B works";
    }
}

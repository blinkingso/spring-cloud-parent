package com.yz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring security mvcMatchers using Spring Expression
 *
 * @author andrew
 * @date 2020-11-01
 */
@RestController
@RequestMapping
@Slf4j
public class ExpController {

    // Spring Expression
    @GetMapping("/product/{code}")
    public String code(@PathVariable String code) {
        log.info("now we are logging with code " + code);
        return code;
    }

    @GetMapping("/video/{country}/{language}")
    public String video(@PathVariable String country, @PathVariable String language) {
        log.info("video findings on");
        return "Find Video from " + country + " and language is " + language;
    }
}

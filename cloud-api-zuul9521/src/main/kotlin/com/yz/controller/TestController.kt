package com.yz.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-16
 */
@RestController
class TestController {

    @GetMapping("/client")
    fun add(a: Int, b: Int): String {
        return "本地跳转: ${a + b}"
    }
}
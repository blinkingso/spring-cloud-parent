package com.yz.payment.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-23
 */
@RestController
@RequestMapping
class TController {

    @GetMapping("/limit/test")
    fun limit(): String {
        return "limit pass"
    }

    @GetMapping("/limit/redis")
    fun redis(): String {
        return "limit redis pass"
    }
}
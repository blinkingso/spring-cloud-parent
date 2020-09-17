package com.yz.payment.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-17
 */
@RestController
class OauthController {

    @GetMapping("/test/read")
    fun read(): String {
        return "read success"
    }

    @GetMapping("/test/write")
    fun write(): String {
        return "write success"
    }

    @PostMapping("/test/write2")
    fun write2(): String {
        return "write post success"
    }
}
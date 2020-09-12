package com.yz.order.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 *
 * @author andrew
 * @date 2020-09-11
 */
@FeignClient(value = "PROVIDER-HYSTRIX-PAYMENT", path = "/payment")
interface OrderPaymentService {

    @GetMapping("/ok/{id}")
    fun ok(@PathVariable("id") id: String): String

    @GetMapping("/timeout/{id}")
    fun timeout(@PathVariable("id") id: String): String
}
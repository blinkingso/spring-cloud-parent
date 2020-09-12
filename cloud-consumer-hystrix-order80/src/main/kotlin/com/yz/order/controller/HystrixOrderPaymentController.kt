package com.yz.order.controller

import com.yz.order.service.OrderPaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-11
 */
@RestController
@RequestMapping("/hystrix")
class HystrixOrderPaymentController {

    @Autowired
    private lateinit var orderPaymentService: OrderPaymentService

    @GetMapping("/order/ok/{id}")
    fun ok(@PathVariable("id") id: String): String {
        return orderPaymentService.ok(id)
    }

    @GetMapping("/order/timeout/{id}")
    fun timeout(@PathVariable("id") id: String): String {
        return orderPaymentService.timeout(id)
    }
}
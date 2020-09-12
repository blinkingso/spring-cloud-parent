package com.yz.payment.controller

import com.yz.payment.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-10
 */
@RestController
@RequestMapping("/payment")
class PaymentController {

    @Autowired
    private lateinit var paymentService: PaymentService

    @Value("\${server.port}")
    private lateinit var serverPort: String

    @GetMapping("/ok/{id}")
    fun ok(@PathVariable("id") id: String): String {
        return paymentService.ok(id)
    }

    @GetMapping("/timeout/{id}")
    fun timeout(@PathVariable("id") id: String): String {
        return paymentService.timeout(id)
    }
}
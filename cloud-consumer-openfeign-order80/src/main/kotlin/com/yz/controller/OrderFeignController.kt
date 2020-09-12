package com.yz.controller

import com.yz.commons.entities.CommonResult
import com.yz.commons.entities.Payment
import com.yz.service.PaymentFeignService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * requestMapping
 * @author andrew
 * @date 2020-09-10
 */
@RestController
@RequestMapping("/order")
class OrderFeignController {

    @Autowired
    private lateinit var paymentFeignService: PaymentFeignService

    @GetMapping("/payment/find")
    fun findAll(): CommonResult<List<Payment>?> {
        return paymentFeignService.findAll()
    }

    @GetMapping("/payment/find/{id}")
    fun findById(@PathVariable("id") id: Long): CommonResult<Payment?> {
        return paymentFeignService.findById(id)
    }

    @PostMapping("/payment/create")
    fun create(@RequestBody payment: Payment): CommonResult<Payment> {
        return CommonResult(code = 200, message = "success")
    }

    @GetMapping("/payment/timeout")
    fun timeout(): String {
        return paymentFeignService.timeout()
    }
}
package com.yz.service

import com.yz.commons.entities.CommonResult
import com.yz.commons.entities.Payment
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 *
 * @author andrew
 * @date 2020-09-10
 */
@Component
@FeignClient(name = "CLOUD-PROVIDER-PAYMENT")
interface PaymentFeignService {

    @GetMapping("/payment/find")
    fun findAll(): CommonResult<List<Payment>?>

    @GetMapping("/payment/find/{id}")
    fun findById(@PathVariable("id") id: Long): CommonResult<Payment?>

    @GetMapping("/payment/timeout")
    fun timeout(): String
}
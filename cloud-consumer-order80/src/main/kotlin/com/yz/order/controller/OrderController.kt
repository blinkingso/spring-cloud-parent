package com.yz.order.controller

import com.yz.commons.entities.CommonResult
import com.yz.commons.entities.Payment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.net.URI

@RestController
@RequestMapping(value = ["/order"])
class OrderController {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    //    private val paymentURL = "http://localhost:8001"
    // RestTemplate Bean 添加@LoadBalanced注解
    private val paymentURL = "http://CLOUD-PROVIDER-PAYMENT"

    @GetMapping("/payment/create")
    fun create(payment: Payment): CommonResult<Payment>? {
        return restTemplate.postForObject(paymentURL.plus("/payment/create"),
                payment,
                CommonResult<Payment>().javaClass)
    }

    @GetMapping("/payment/find")
    fun findAll(): CommonResult<List<Payment>>? {
        return restTemplate.getForObject(paymentURL.plus("/payment/find"),
                CommonResult<List<Payment>>().javaClass)
    }

    @GetMapping("/payment/find/{id}")
    fun findPaymentById(@PathVariable("id") id: Long): CommonResult<Payment>? {
        return restTemplate.getForObject(paymentURL.plus("/payment/find/${id}"),
                CommonResult<Payment>().javaClass)
    }
}

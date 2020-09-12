package com.yz.payment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix

/**
 *
 * @author andrew
 * @date 2020-09-10
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
class PaymentHystrix8003

fun main(args: Array<String>) {
    runApplication<PaymentHystrix8003>(*args)
}
package com.yz.payment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class PaymentProvider8001

fun main(args: Array<String>) {
    runApplication<PaymentProvider8001>(*args)
}
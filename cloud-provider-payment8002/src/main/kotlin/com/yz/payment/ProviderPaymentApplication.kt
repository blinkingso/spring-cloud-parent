package com.yz.payment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
class PaymentProvider8002

fun main(args: Array<String>) {
    runApplication<PaymentProvider8002>(*args)
}
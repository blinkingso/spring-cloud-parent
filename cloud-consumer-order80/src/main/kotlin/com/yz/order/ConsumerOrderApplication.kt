package com.yz.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.ribbon.RibbonClient

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(value = "CLOUD-PROVIDER-PAYMENT", name = "randomPaymentRule")
class ConsumerOrderApplication

fun main(args: Array<String>) {
    runApplication<ConsumerOrderApplication>(*args)
}
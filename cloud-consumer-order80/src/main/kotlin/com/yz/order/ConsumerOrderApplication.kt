package com.yz.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ConsumerOrderApplication

fun main(args: Array<String>) {
    runApplication<ConsumerOrderApplication>(*args)
}
package com.yz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients

/**
 * 被发现
 * @author andrew
 * @date 2020-09-10
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
class ConsumerOpenFeign80

fun main(args: Array<String>) {
    runApplication<ConsumerOpenFeign80>(*args)
}
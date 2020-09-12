package com.yz.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.cloud.openfeign.EnableFeignClients

/**
 *
 * @author andrew
 * @date 2020-09-11
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
class HystrixOrder80

fun main(args: Array<String>) {
    runApplication<HystrixOrder80>(*args)
}
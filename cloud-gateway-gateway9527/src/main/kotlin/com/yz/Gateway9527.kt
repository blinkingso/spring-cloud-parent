package com.yz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

/**
 * spring cloud gateway
 * 新一代的网关路由, 替换zuul1
 * @author andrew
 * @date 2020-09-21
 */
@SpringBootApplication
@EnableEurekaClient
class Gateway9527

fun main() {
    runApplication<Gateway9527>()
}
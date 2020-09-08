package com.yz.eureka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class EurekaServer7002

fun main(args: Array<String>) {
    runApplication<EurekaServer7002>(*args)
}
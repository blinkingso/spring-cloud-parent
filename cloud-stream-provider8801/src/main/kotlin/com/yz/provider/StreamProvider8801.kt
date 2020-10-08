package com.yz.provider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

/**
 *
 * @author andrew
 * @date 2020-09-29
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
class StreamProvider8801

fun main() {
    runApplication<StreamProvider8801>()
}
package com.yz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

/**
 *
 * @author andrew
 * @date 2020-09-17
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
class Oauth2Server7777

fun main() {
    runApplication<Oauth2Server7777>()
}
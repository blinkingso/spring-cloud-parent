package com.yz

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
class ConfigClient9900 {
    @Value("\${foo}")
    private lateinit var foo: String

    @GetMapping("/foo")
    fun hi(): String {
        return foo
    }
}

fun main() {
    runApplication<ConfigClient9900>()
}
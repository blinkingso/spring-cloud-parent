package com.yz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import zipkin2.server.internal.EnableZipkinServer

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableWebMvc
@EnableZipkinServer
class ZipkinServer9411

fun main() {
    runApplication<ZipkinServer9411>()
}
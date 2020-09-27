package com.yz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

/**
 * 配置服务中心
 * @author andrew
 * @date 2020-09-25
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
class ConfigServer9001

fun main() {
    runApplication<ConfigServer9001>()
}
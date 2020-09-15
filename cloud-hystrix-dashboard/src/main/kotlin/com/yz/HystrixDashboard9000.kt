package com.yz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard

/**
 *
 * @author andrew
 * @date 2020-09-14
 */
@SpringBootApplication
@EnableHystrixDashboard
class HystrixDashboard9000

fun main(args: Array<String>) {
    runApplication<HystrixDashboard9000>(*args)
}
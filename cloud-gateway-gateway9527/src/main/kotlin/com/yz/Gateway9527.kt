package com.yz

import com.yz.filter.RemoteAddrKeyResolver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.context.annotation.Bean

/**
 * spring cloud gateway
 * 新一代的网关路由, 替换zuul1
 * @author andrew
 * @date 2020-09-21
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
class Gateway9527 {

    @Bean(name = [RemoteAddrKeyResolver.BEAN_NAME])
    fun remoteAddrKeyResolver() = RemoteAddrKeyResolver()
}

fun main() {
    runApplication<Gateway9527>()
}
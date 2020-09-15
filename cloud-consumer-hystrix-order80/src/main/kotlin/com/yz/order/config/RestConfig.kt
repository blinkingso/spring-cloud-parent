package com.yz.order.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

/**
 *
 * @author andrew
 * @date 2020-09-14
 */
@Configuration
class RestConfig {

    @Bean
    @LoadBalanced
    fun restTemplate(): RestTemplate = RestTemplate()
}
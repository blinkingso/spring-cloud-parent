package com.yz.rule

import com.netflix.loadbalancer.IRule
import com.netflix.loadbalancer.RandomRule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RibbonRuleConfiguration

@Bean(name = ["randomPaymentRule"])
fun randomRule(): IRule = RandomRule()
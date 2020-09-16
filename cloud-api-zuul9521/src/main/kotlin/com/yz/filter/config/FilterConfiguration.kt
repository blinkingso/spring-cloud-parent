package com.yz.filter.config

import com.netflix.zuul.ZuulFilter
import com.yz.filter.PostFilter
import com.yz.filter.SecondPreFilter
import com.yz.filter.ThirdPreFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author andrew
 * @date 2020-09-16
 */
@Configuration
class FilterConfiguration {

    @Bean
    fun secondPreFilter(): ZuulFilter = SecondPreFilter()

    @Bean
    fun thirdPreFilter(): ZuulFilter = ThirdPreFilter()

    @Bean
    fun postFilter(): ZuulFilter = PostFilter()
}
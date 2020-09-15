package com.yz.order.config

import com.yz.order.interceptor.InitialHystrixCacheContextInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 启用拦截器, 拦截所有的请求
 * @author andrew
 * @date 2020-09-14
 */
@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(InitialHystrixCacheContextInterceptor()).addPathPatterns("/**")
    }
}
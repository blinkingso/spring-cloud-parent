package com.yz.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * java 流式配置网关路由
 * @author andrew
 * @date 2020-09-21
 */
@Configuration
class RoutesConfig {

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return with(builder.routes()) {
            // basic proxy
            this.route("jd_route") {
                it.path("/find/**").and().header("auth", "123123").uri("http://localhost:8001")
            }

            // 请求时间在前一个分钟之后
            // 比如某秒杀活动在一小时后开启访问
            val preHour = LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault())
            // after 1小时 后的UTC时间
            this.route("after_route") {
                it.after(preHour).uri("http://localhost:8001")
            }

            // 按顺序执行转发后不再继续转发?

            // before
            val afterHour = LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault())
            // 在1小时后的时间的之前可以访问
            // 比如某个活动在服务启动后的一个小时内有效, 那么从服务开启时延后1一个小时内可以请求, 之后请求则被拒绝
            this.route("before_route") {
                it.before(afterHour).uri("http://www.baidu.com")
            }

            // 两个时间段之间
            this.route("between_route") {
                it.between(preHour, afterHour).uri("http://jd.com")
            }

            this.build()
        }
    }
}
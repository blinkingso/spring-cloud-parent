package com.yz.config

import com.netflix.hystrix.HystrixCommandGroupKey
import com.netflix.hystrix.HystrixCommandKey
import com.netflix.hystrix.HystrixCommandProperties
import com.netflix.hystrix.HystrixObservableCommand
import com.yz.filter.RequestTimeFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.net.URLEncoder
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * java 流式配置网关路由
 * @author andrew
 * @date 2020-09-21
 */
@Configuration
class RoutesConfig {

//    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return with(builder.routes()) {
            // basic proxy
//            this.route("jd_route") {
//                it.path("/find/**").and().header("auth", "123123").uri("http://localhost:8001")
//            }

            // 请求时间在前一个分钟之后
            // 比如某秒杀活动在一小时后开启访问
            val preHour = LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault())
            // after 1小时 后的UTC时间
            this.route("after_route") {
                it.after(preHour).and().path("/").filters { f ->
                    f.addRequestParameter("requestTime", URLEncoder.encode(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), "UTF-8"))
                            .addRequestParameter("endTime", URLEncoder.encode(LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), "UTF-8"))
                }.uri("http://localhost:8001")
            }

            // 按顺序执行转发后不再继续转发?

            // before
            val afterHour = LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault())
            // 在1小时后的时间的之前可以访问
            // 比如某个活动在服务启动后的一个小时内有效, 那么从服务开启时延后1一个小时内可以请求, 之后请求则被拒绝
            this.route("before_route") {
                it.before(afterHour).and().path("/").uri("http://www.baidu.com")
            }

            // 两个时间段之间
            this.route("between_route") {
                it.between(preHour, afterHour).and().path("/").uri("http://jd.com")
            }

            // 断言中匹配cookie是否存在, cookie匹配成功时执行转发
            this.route("cookie_route") {
                it.cookie("Auth", "cookie-auth").and().path("/").uri("http://localhost:8001")
            }

            // Header路由断言工厂
            this.route("header_route") {
                it.header("Authorization", "#admin").and().path("/").uri("http://sina.com.cn")
            }

            // Host Predicate Factory
            this.route("host_route") {
                it.host("127.0.0.1").and().path("/").uri("http://localhost:8002")
            }

            // 对请求方式进行断言匹配
            this.route("method_route") {
                it.method(HttpMethod.PUT, HttpMethod.HEAD, HttpMethod.DELETE).and().path("/")
                        .uri("http://localhost:8002")
            }

            // Query路由断言工厂 获取请求中的参数并进行断言匹配
            this.route("query_route") {
                it.query("key", "123").and().path("/").uri("http://localhost:8001")
            }

            // RemoteAddr 路由断言工厂
            this.route("remoteAddr_route") {
                it.remoteAddr("127.0.0.1").and().path("/").uri("http://baidu.com")
            }

            // RewritePath过滤器
            // 去掉order前缀转发访问
            this.route("rewrite_path_and_remote_route") {
                it.remoteAddr("localhost").and().path("/order/**")
                        .filters { f ->
                            f.rewritePath("/order/(?<segment>.*)", "/$\\{segment}")
                            f.addResponseHeader("X-Response-Foo", "Bar")

                            // StripPrefix过滤器, 用于去除前缀/order/**后面去除order后面的第一个
                            f.stripPrefix(1)
                        }
                        .uri("http://localhost:8001")
            }

            // retries
            this.route("retry_filter_route") {
                it.path("/test/retry").filters { f ->
                    // retry 过滤器
                    f.retry { conf ->
                        conf.retries = 2
                        // 重试两次后仍失败时返回500的错误
                        conf.setStatuses(HttpStatus.INTERNAL_SERVER_ERROR)
                    }
                }.uri("http://localhost:8001/retry?key=abc&count=2")
            }

            // hystrix 过滤器
            this.route("hystrix_filter_route") {
                it.path("/test/hystrix").filters { f ->
                    f.hystrix { conf ->
                        val commandKey = "fallbackCmd"
                        conf.name = commandKey
                        conf.setFallbackUri("forward:/fallback")
                        val setter = HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("default"))
                        setter.andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                        setter.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(4000))
                        conf.setSetter(setter)
                    }
                }.uri("http://localhost:8001")
            }

            // 统计时间
            this.route("request_time_route") {
                it.path("/test/**").filters { f ->
                    f.filter(RequestTimeFilter())
                }.uri("lb://cloud-consumer-openfeign-order")
            }

            // 限流接口
            this.route("rate_limit_route") {
                it.path("/limit/test").filters { f ->
                    f.filter(BucketRateLimiterFilter(10, 1, Duration.ofSeconds(1)))
//                    f.hystrix { conf ->
//                        val commandKey = "fallbackCmd"
//                        conf.name = commandKey
//                        conf.setFallbackUri("forward:/fallback")
//                        val setter = HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("default"))
//                        setter.andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
//                        setter.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(4000))
//                        conf.setSetter(setter)
//                    }
                }.uri("lb://CLOUD-PROVIDER-PAYMENT")
            }

            this.build()
        }
    }
}
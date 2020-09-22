package com.yz.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.core.Ordered
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * 统计某个或某种路由的处理时长
 * @author andrew
 * @date 2020-09-22
 */
class RequestTimeFilter : GatewayFilter, Ordered {

    private val logger = LoggerFactory.getLogger(RequestTimeFilter::class.java)
    private val countStartTime = "countStartTime"

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        exchange.attributes[countStartTime] = System.currentTimeMillis()
        return chain.filter(exchange).then(
                Mono.fromRunnable {
                    val startTime = exchange.getAttribute<Long>(countStartTime)!!
                    val endTime = System.currentTimeMillis() - startTime
                    logger.info("${exchange.request.uri.rawPath} 耗时: $endTime ms")
                })
    }

    override fun getOrder(): Int = Ordered.LOWEST_PRECEDENCE
}
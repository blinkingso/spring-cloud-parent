package com.yz.filter

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.MetricsEndpoint
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*

/**
 * 根据cpu的使用情况进行限流
 * @author andrew
 * @date 2020-09-24
 */
@Component
class CpuUsageRateLimiterFilter : GatewayFilter, Ordered {

    private val logger = LoggerFactory.getLogger(CpuUsageRateLimiterFilter::class.java)

    @Autowired
    private lateinit var metricsEndpoint: MetricsEndpoint

    // cpu 指标名
    private val metricName = "system.cpu.usage"

    // 指标值
    private val maxUsage = 0.50

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val systemCpuUsage = metricsEndpoint.metric(metricName, null)
                .measurements
                .stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(MetricsEndpoint.Sample::getValue)
                .filter(Double::isFinite)
                .orElse(0.0)

        val isOpenRateLimit = systemCpuUsage > maxUsage

        logger.debug("system.cpu.usage: $systemCpuUsage, isOpenRateLimit: $isOpenRateLimit")

        return if (isOpenRateLimit) {
            // 受限
            exchange.response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS)
            exchange.response.setComplete()
        } else {
            chain.filter(exchange)
        }
    }

    override fun getOrder(): Int = -1001
}
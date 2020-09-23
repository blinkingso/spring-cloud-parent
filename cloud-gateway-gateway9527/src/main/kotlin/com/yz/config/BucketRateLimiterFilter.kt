package com.yz.config

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Bucket4j
import io.github.bucket4j.Refill
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

/**
 * 接口限流
 * @author andrew
 * @date 2020-09-23
 */
class BucketRateLimiterFilter(
        // 桶的最大容量, 即能装载token的最大数量
        private val capacity: Int,
        // 每次token的补充量
        private val refillTokens: Int,
        // 补充token的时间间隔
        private val refillDuration: Duration
) : GatewayFilter, Ordered {

    private val logger = LoggerFactory.getLogger(BucketRateLimiterFilter::class.java)

    private val cache = ConcurrentHashMap<String, Bucket>()

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val ip = exchange.request.remoteAddress?.address?.hostAddress
        if (ip.isNullOrEmpty()) {
            logger.error("未获取到客户端ip地址")
            exchange.response.statusCode = HttpStatus.BAD_REQUEST
            return exchange.response.setComplete()
        }

        // ip cache
        val bucket = cache.computeIfAbsent(ip) { this.createNewBucket() }
        logger.debug("IP:$ip, 令牌桶可用的token数量: ${bucket.availableTokens}")
        return if (bucket.tryConsume(1)) {
            // 拿到令牌
            chain.filter(exchange)
        } else {
            exchange.response.statusCode = HttpStatus.TOO_MANY_REQUESTS
            exchange.response.setComplete()
        }
    }

    override fun getOrder(): Int = -1000

    private fun createNewBucket(): Bucket {
        return with(Refill.greedy(refillTokens.toLong(), refillDuration)) {
            val limit = Bandwidth.classic(capacity.toLong(), this)
            Bucket4j.builder().addLimit(limit).build()
        }
    }
}
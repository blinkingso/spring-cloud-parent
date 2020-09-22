package com.yz.filter

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * auth token global filter
 * 全局filter
 * @author andrew
 * @date 2020-09-22
 */
@Component
class AuthTokenGFilter : GlobalFilter, Ordered {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val token = exchange.request.queryParams["authToken"]?.toString()
        if (token.isNullOrEmpty()) {
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            return exchange.response.setComplete()
        }

        // 校验通过后向后执行
        return chain.filter(exchange)
    }

    override fun getOrder(): Int = -100
}
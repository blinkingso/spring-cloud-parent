package com.yz.filter

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 *
 * @author andrew
 * @date 2020-09-24
 */
class RemoteAddrKeyResolver : KeyResolver {

    override fun resolve(exchange: ServerWebExchange): Mono<String> {
        return Mono.just(exchange.request.remoteAddress!!.address.hostAddress)
    }

    companion object {
        const val BEAN_NAME = "remoteAddrKeyResolver"
    }
}
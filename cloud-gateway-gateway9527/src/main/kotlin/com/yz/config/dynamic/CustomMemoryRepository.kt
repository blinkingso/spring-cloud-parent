package com.yz.config.dynamic

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.cloud.gateway.route.RouteDefinitionRepository
import org.springframework.cloud.gateway.support.NotFoundException
import org.springframework.util.StringUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 *
 * @author andrew
 * @date 2020-09-24
 */
class CustomMemoryRepository : RouteDefinitionRepository {

    private val logger = LoggerFactory.getLogger(CustomMemoryRepository::class.java)

    private val routes = Collections.synchronizedMap(
            LinkedHashMap<String, RouteDefinition>())

    override fun save(route: Mono<RouteDefinition>): Mono<Void> {
        logger.debug("动态新增路由... id为: ${route.map(RouteDefinition::getId)}")
        return route.flatMap { r: RouteDefinition ->
            if (StringUtils.isEmpty(r.id)) {
                logger.debug("id 不能为空")
                return@flatMap Mono.error<Void>(IllegalArgumentException("id may not be empty"))
            }
            routes[r.id] = r
            Mono.empty<Void>()
        }
    }

    override fun delete(routeId: Mono<String>): Mono<Void> {
        return routeId.flatMap { id: String ->
            if (routes.containsKey(id)) {
                routes.remove(id)
                return@flatMap Mono.empty<Void>()
            }
            Mono.defer {
                Mono.error<Void>(
                        NotFoundException("RouteDefinition not found: $routeId"))
            }
        }
    }

    override fun getRouteDefinitions(): Flux<RouteDefinition> {
        return Flux.fromIterable(routes.values)
    }
}
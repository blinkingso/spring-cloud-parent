package com.yz.config.dynamic

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository
import org.springframework.cloud.gateway.route.RouteDefinitionRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author andrew
 * @date 2020-09-24
 */
@Configuration
class DynamicRoutesConfiguration {

    @Bean
    fun routeDefinitionRepository(): RouteDefinitionRepository {
        return CustomMemoryRepository()
    }
}
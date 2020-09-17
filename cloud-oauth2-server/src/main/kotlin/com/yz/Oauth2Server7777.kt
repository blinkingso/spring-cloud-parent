package com.yz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 *
 * @author andrew
 * @date 2020-09-17
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
class Oauth2Server7777 : WebSecurityConfigurerAdapter() {

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        with(auth) {
            inMemoryAuthentication()
                    .withUser("guest").password("guest").authorities("message.read")
                    .and()
                    .withUser("admin").password("admin").authorities("message.read", "message.write")
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()
}

fun main() {
    runApplication<Oauth2Server7777>()
}
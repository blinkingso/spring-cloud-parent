package com.yz.payment.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 *
 * @author andrew
 * @date 2020-09-17
 */
@EnableResourceServer
@Configuration
class Oauth2Configure : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        with(http) {
            csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/test/read")
                    .hasAuthority("message.read")
                    .antMatchers(HttpMethod.GET, "/test/write")
                    .hasAuthority("message.write")
        }
    }

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId("messages-resource")
                .tokenStore(jwtTokenStore())
    }

    @Bean
    fun jwtTokenConverter(): JwtAccessTokenConverter {
        val jtc = JwtAccessTokenConverter()
        jtc.setSigningKey("springcloud123123")
        return jtc
    }

    @Bean
    fun jwtTokenStore(): JwtTokenStore = JwtTokenStore(jwtTokenConverter())
}
package com.yz.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import javax.annotation.Resource

/**
 *
 * @author andrew
 * @date 2020-09-17
 */
@Configuration
@EnableAuthorizationServer
class Oauth2Configuration : AuthorizationServerConfigurerAdapter() {

    @Resource
    private lateinit var authenticationManager: AuthenticationManager

    @Bean
    protected fun jwtTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey("springcloud123123")
        return converter
    }

    @Bean
    fun jwtTokenStore() = JwtTokenStore(jwtTokenConverter())

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("cloud-zuul9521")
                .secret("jwtoauth2")
                .scopes("read", "write").autoApprove(true)
                .authorities("message.read", "message.write")
                .resourceIds("messages-resource")
                .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code")
                .redirectUris("http://localhost:9521/client?a=1&b=2")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        with(endpoints) {
            tokenStore(jwtTokenStore())
            tokenEnhancer(jwtTokenConverter())
            authenticationManager(authenticationManager)
        }
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.allowFormAuthenticationForClients()
    }
}
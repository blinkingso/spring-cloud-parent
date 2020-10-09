package com.yz.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler
import org.springframework.security.oauth2.provider.approval.InMemoryApprovalStore
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 *
 * @author andrew
 * @date 2020-09-17
 */
@Configuration
@EnableAuthorizationServer
class Oauth2Configuration : AuthorizationServerConfigurerAdapter() {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var clientDetailsService: ClientDetailsService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Bean
    protected fun jwtTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey("springcloud123123")
        return converter
    }

    @Bean
    fun jwtTokenStore(): JwtTokenStore {
        return with(JwtTokenStore(jwtTokenConverter())) {
            setApprovalStore(approvalStore())
            this
        }
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("cloud-zuul9521")
                .secret(passwordEncoder.encode("jwtoauth2"))
                .scopes("read", "write", "delete").autoApprove(true)
                .authorities("GUEST", "ADMIN")
                .resourceIds("messages-resource")
                .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code")
                .redirectUris("http://localhost:9521/client?a=1&b=2")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(jwtTokenStore())
                .tokenEnhancer(jwtTokenConverter())
                .authenticationManager(authenticationManager)
                .userApprovalHandler(approvalHandler())
    }

    @Bean
    fun approvalHandler(): UserApprovalHandler {
        return with(ApprovalStoreUserApprovalHandler()) {
            setApprovalStore(approvalStore())
            setClientDetailsService(clientDetailsService)
            setRequestFactory(DefaultOAuth2RequestFactory(clientDetailsService))

            this
        }
    }

    @Bean
    fun approvalStore(): ApprovalStore = InMemoryApprovalStore()

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.allowFormAuthenticationForClients()
    }
}
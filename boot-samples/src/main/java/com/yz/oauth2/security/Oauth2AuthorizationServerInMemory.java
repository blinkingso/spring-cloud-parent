package com.yz.oauth2.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * 基于内存缓存模式的security方式
 *
 * @author andrew
 * @date 2020-10-10
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class Oauth2AuthorizationServerInMemory extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public Oauth2AuthorizationServerInMemory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // 客户端请求访问限制
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        log.info("密码为: " + passwordEncoder.encode("secret"));
        clients.inMemory()
                // 客户端id
                .withClient("side-search")
                // 客户端请求访问控制的秘钥
                .secret(passwordEncoder.encode("secret"))
                // 重定向地址
                .redirectUris("http://localhost:9000/callback")
                // 定义认证授权方式为: code
                .authorizedGrantTypes("authorization_code", "implicit", "password")
                // token 有效时间120s
                .accessTokenValiditySeconds(120)
                // 权限作用域, 比如读写删除等或者读取联系人等
                .scopes("read", "write", "contacts");
    }


    // authorizedGrantTypes: password 授权方式需要注入AuthenticationManager
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}

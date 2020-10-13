package com.yz.oauth2.security;

import com.yz.oauth2.PrintIOCBeansAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import java.util.Arrays;

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

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private PrintIOCBeansAware printIOCBeansAware;

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


        Arrays.stream(this.printIOCBeansAware.getApplicationContext().getBeanDefinitionNames()).forEach(s -> {
            log.info("现在容器中的bean为: " + s + "====>" + this.printIOCBeansAware.getApplicationContext().getBean(s));

            if ("springSecurityFilterChain".equals(s)) {
                log.info("Spring Security 安全控制的Filter为: " + this.printIOCBeansAware.getApplicationContext().getBean(s).getClass());
            }
        });
    }
}

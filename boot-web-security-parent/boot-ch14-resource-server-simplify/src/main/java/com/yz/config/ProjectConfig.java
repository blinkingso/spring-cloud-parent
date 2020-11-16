package com.yz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author andrew
 * @date 2020-11-16
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 拦截所有的请求
        http.authorizeRequests().anyRequest().authenticated();
        // 配置Oauth2ResourceServer过滤器
        http.oauth2ResourceServer(c -> c.opaqueToken(o -> {
            o.introspectionUri("http://localhost:9090/oauth/check_token");
            o.introspectionClientCredentials("db_client_authorization_code",
                    "123456");
            // black boarding
        }));
    }
}

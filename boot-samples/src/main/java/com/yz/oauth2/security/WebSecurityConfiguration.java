package com.yz.oauth2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 原理解析:
 * springSecurityFilterChain的Filter
 * <p>
 * -> user
 * -> request
 * -> UsernamePasswordAuthenticationFilter(基于表单用户校验)
 * -> BasicAuthenticationFilter(基于httpBasic方式校验)
 * -> ExceptionTranslationFilter(异常处理) -> ... -> FilterSecurityInterceptor(总拦截器) -> 目标资源(Controllers)
 * -> 响应到用户
 * -> user
 *
 * @author andrew
 * @date 2020-10-10
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public InMemoryUserDetailsServices userDetailsServices() {
        return new InMemoryUserDetailsServices(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsServices();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return userDetailsServices();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
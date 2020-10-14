package com.yz.oauth2.security;

import com.yz.oauth2.security.filter.YzOriginAuthenticationFilter;
import com.yz.oauth2.security.provider.YzAuthenticationProvider;
import com.yz.oauth2.security.provider.YzRemoteOriginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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

    public WebSecurityConfiguration(UserDetailsService userDetailsService,
                                    PasswordEncoder passwordEncoder,
                                    YzAuthenticationProvider yzAuthenticationProvider,
                                    YzRemoteOriginAuthenticationProvider yzRemoteOriginAuthenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.yzAuthenticationProvider = yzAuthenticationProvider;
        this.yzRemoteOriginAuthenticationProvider = yzRemoteOriginAuthenticationProvider;
    }

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final YzRemoteOriginAuthenticationProvider yzRemoteOriginAuthenticationProvider;
    private final YzAuthenticationProvider yzAuthenticationProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(new YzOriginAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // provider 添加到AuthenticationManager中
        auth.authenticationProvider(yzAuthenticationProvider);
        auth.authenticationProvider(yzRemoteOriginAuthenticationProvider);
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
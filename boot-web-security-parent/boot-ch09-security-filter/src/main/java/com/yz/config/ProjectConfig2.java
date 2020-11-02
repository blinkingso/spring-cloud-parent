package com.yz.config;

import com.yz.filters.StaticKeyAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author andrew
 * @date 2020-10-30
 */
@Configuration
public class ProjectConfig2 extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) {
        http.addFilterAt(new StaticKeyAuthenticationFilter(), BasicAuthenticationFilter.class);
    }
}

package com.yz.config;

import com.yz.authentication.provider.OtpAuthenticationProvider;
import com.yz.authentication.provider.UsernamePasswordAuthenticationProvider;
import com.yz.filter.JwtAuthenticationFilter;
import com.yz.filter.PreAuthFilter;
import com.yz.proxy.AuthenticationServerProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author andrew
 * @date 2020-11-04
 */
@Configuration
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Value("${sign.key}")
    private String signKey;
    private final AuthenticationServerProxy proxy;

    public SecurityConfigure(AuthenticationServerProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilterAt(new PreAuthFilter(this.signKey, authenticationManagerBean()),
                BasicAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthenticationFilter(this.signKey), BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // register providers
        auth.authenticationProvider(new UsernamePasswordAuthenticationProvider(this.proxy))
                .authenticationProvider(new OtpAuthenticationProvider(this.proxy));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

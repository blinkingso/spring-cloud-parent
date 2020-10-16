package com.yz.config;

import com.yz.dao.OtpRepository;
import com.yz.security.cache.TokenCache;
import com.yz.security.filter.YzOptAuthenticationRequestFilter;
import com.yz.security.filter.YzTokenAuthenticationFilter;
import com.yz.security.providers.YzOtpAuthenticationProvider;
import com.yz.security.providers.YzTokenAuthenticationProvider;
import com.yz.security.providers.YzUsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author andrew
 * @date 2020-10-15
 */
@Configuration
@EnableWebMvc
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
@Import({ProjectConfig.class})
@ConditionalOnBean(TokenCache.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final OtpRepository otpRepository;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, OtpRepository otpRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.otpRepository = otpRepository;
    }

    @Autowired
    private TokenCache tokenCache;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(new YzOtpAuthenticationProvider(this.userDetailsService, this.otpRepository));
        auth.authenticationProvider(new YzUsernamePasswordAuthenticationProvider(this.userDetailsService, this.passwordEncoder));
        auth.authenticationProvider(new YzTokenAuthenticationProvider(tokenCache));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(new YzOptAuthenticationRequestFilter(this.authenticationManagerBean(),
                        this.otpRepository, this.tokenCache),
                BasicAuthenticationFilter.class);
        http.addFilterAt(new YzTokenAuthenticationFilter(this.authenticationManagerBean()), BasicAuthenticationFilter.class);
        super.configure(http);
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return this.userDetailsService;
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

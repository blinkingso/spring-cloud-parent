package com.yz.config;

import com.yz.filter.CsrfTokenLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * @author andrew
 * @date 2020-11-02
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HandlerMappingIntrospector handlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var userDetailsService = new InMemoryUserDetailsManager();
        userDetailsService.createUser(User.withUsername("admin").password(passwordEncoder.encode("admin"))
                .roles("ADMIN").build());
        return userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class);

        // customize CSRF configuration
        http.csrf(conf -> {
            // ant matchers
            conf.ignoringAntMatchers("/ignore");

            // mvcRequestMatcher
            var mvcMatcher = new MvcRequestMatcher(handlerMappingIntrospector(), "/hi");
            conf.ignoringRequestMatchers(mvcMatcher);

            // RegexMatcher
            var regexMatcher = new RegexRequestMatcher(".*[0-9].*", HttpMethod.POST.name());
            conf.ignoringRequestMatchers(regexMatcher);
        });
    }
}

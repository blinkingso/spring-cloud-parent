package com.yz.config;

import com.yz.csrf.YzCsrfRepository;
import com.yz.filter.YzCsrfTokenLoggerFilter;
import com.yz.repository.CsrfTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author andrew
 * @date 2020-10-20
 */
@Configuration
@EnableWebMvc
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    private final CsrfTokenRepository csrfTokenRepository;

    public ProjectConfig(CsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 自定义csrf存储加载, 这里仅测试加入了日志, 委托HttpSessionCsrfTokenRepository的实现

        http
                .authorizeRequests().anyRequest().authenticated()
                .and().csrf(c -> {
            c.ignoringAntMatchers("/csrf/**", "/login/**");
            c.csrfTokenRepository(new YzCsrfRepository(csrfTokenRepository));
        }).addFilterAfter(new YzCsrfTokenLoggerFilter(), CsrfFilter.class)
                .cors(c -> {
                    final CorsConfigurationSource source = req -> {
                        final CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.addAllowedHeader("X-ALLOW-H");
                        corsConfiguration.setAllowedOrigins(Stream.of("http://localhost:8081", "http://localhost:8080").collect(Collectors.toList()));
                        corsConfiguration.setAllowedMethods(Stream.of("GET", "PUT", "POST", "DELETE").collect(Collectors.toList()));
                        return corsConfiguration;
                    };
                    c.configurationSource(source);
                })
                .formLogin()
                .and()
                .httpBasic();
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        final InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin"))
                .roles("ADMIN").authorities("READ", "WRITE").build());
        manager.createUser(User.withUsername("guest").password(passwordEncoder().encode("guest"))
                .roles("GUEST").authorities("READ").build());
        return manager;
    }

    /**
     * 配置AuthenticationManagerBuilder
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception ex
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService());
    }
}

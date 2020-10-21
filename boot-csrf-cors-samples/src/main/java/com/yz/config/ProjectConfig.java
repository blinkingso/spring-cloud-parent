package com.yz.config;

import com.yz.csrf.YzCsrfRepository;
import com.yz.filter.YzCsrfTokenLoggerFilter;
import com.yz.repository.CsrfTokenRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

        http.csrf(c -> {
            c.csrfTokenRepository(new YzCsrfRepository(csrfTokenRepository));
            c.ignoringAntMatchers("/csrf/**");
        })
                // 添加filter在CsrfFilter之后, 打印CsrfToken
                .addFilterAfter(new YzCsrfTokenLoggerFilter(), CsrfFilter.class);

        // 配置cors cross-origin resource sharing
        http.cors(c -> {
            final CorsConfigurationSource source = req -> {
                final CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedHeader("X-ALLOW-H");
                corsConfiguration.setAllowedOrigins(Stream.of("http://localhost:8081", "http://localhost:8080").collect(Collectors.toList()));
                corsConfiguration.setAllowedMethods(Stream.of("GET", "PUT", "POST", "DELETE").collect(Collectors.toList()));
                return corsConfiguration;
            };
            c.configurationSource(source);
        });

        http.authorizeRequests().anyRequest().permitAll();
    }
}

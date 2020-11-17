package com.yz.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;

/**
 * @author andrew
 * @date 2020-11-17
 */
@Configuration
@ConditionalOnProperty(value = "spring.resource.server.origin", havingValue = "true", matchIfMissing = true)
public class ResourceServerOriginConfigurer extends WebSecurityConfigurerAdapter {

    private final JwtDecoder jwtDecoder;

    public ResourceServerOriginConfigurer(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();

        // config oauth2 resource server
        http.oauth2ResourceServer(rs -> rs.jwt(jwt -> jwt.decoder(this.jwtDecoder)));
    }
}

package com.yz.config;

import com.yz.repository.ClientDetailsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * OAuth2 config
 *
 * @author andrew
 * @date 2020-11-09
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final ClientDetailsRepository clientDetailsRepository;
    private final AuthenticationManager authenticationManager;

    public AuthServerConfig(ClientDetailsRepository clientDetailsRepository, AuthenticationManager authenticationManager) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.authenticationManager = authenticationManager;
    }

    // config beans
    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // user repository
    @Bean
    @Primary
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var userDetailsService = new InMemoryUserDetailsManager();
        userDetailsService.createUser(User.withUsername("xz")
                .password(passwordEncoder.encode("xz"))
                .roles("USER").build());
        userDetailsService.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN").build());
        return userDetailsService;
    }

    // config authentication manager

    // config clients
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("pub")
                .secret(passwordEncoder().encode("pub"))
                .authorizedGrantTypes("authentication_code")
                .scopes("read", "write")
                .redirectUris("http://localhost:8080")
                .accessTokenValiditySeconds(60)
                .refreshTokenValiditySeconds(60)
                .authorities("ADMIN")
                .resourceIds("rid_pub");

        clients.withClientDetails(new CustomJDBCClientDetailsServiceImpl(this.clientDetailsRepository));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
        endpoints.userDetailsService(this.userDetailsService(this.passwordEncoder()));
    }
}
package com.yz.config;

import com.yz.repository.ClientDetailsRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * OAuth2 config
 *
 * @author andrew
 * @date 2020-11-09
 */
@Configuration(proxyBeanMethods = false)
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final ClientDetailsRepository clientDetailsRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final TokenStore tokenStore;

    public AuthServerConfig(ClientDetailsRepository clientDetailsRepository,
                            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, TokenStore tokenStore) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenStore = tokenStore;
    }

    // config authentication manager

    // config clients
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("pub")
                .secret(passwordEncoder.encode("pub"))
                .authorizedGrantTypes("authentication_code")
                .scopes("read", "write")
                .redirectUris("http://localhost:8080")
                .accessTokenValiditySeconds(24 * 60 * 60)
                .refreshTokenValiditySeconds(24 * 60 * 60)
                .authorities("ADMIN")
                .resourceIds("rid_pub");

        clients.withClientDetails(new CustomJDBCClientDetailsServiceImpl(this.clientDetailsRepository));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
        endpoints.userDetailsService(this.userDetailsService);
        if (tokenStore != null) {
            endpoints.tokenStore(tokenStore);
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // check_token endpoints access check
        // use permitAll() instead of isAuthenticated() for access endpoints without authentication
        security.checkTokenAccess("isAuthenticated()");
    }
}
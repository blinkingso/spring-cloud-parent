package com.yz.config;

import com.yz.pojo.YzTokenEnhancer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.List;

/**
 * @author andrew
 * @date 2020-11-17
 */
@Configuration
@EnableAuthorizationServer
public class JWTOauth2Configure extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final AccessTokenConverter accessTokenConverter;
    private final AuthenticationManager authenticationManager;

    public JWTOauth2Configure(PasswordEncoder passwordEncoder, TokenStore tokenStore,
                              AccessTokenConverter accessTokenConverter,
                              AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.accessTokenConverter = accessTokenConverter;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // config clients
        clients.inMemory()
                .withClient("jwt_client")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .secret(passwordEncoder.encode("12345"))
                .redirectUris("http://localhost:8080")
                .autoApprove(true)
                .scopes("read", "write");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // enhance token
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        var tokenEnhancers = List.of(new YzTokenEnhancer(), (JwtAccessTokenConverter) this.accessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        // jwt token store and token converter
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenStore(this.tokenStore)
//                .accessTokenConverter(this.accessTokenConverter);
                // enhance token
        .tokenEnhancer(tokenEnhancerChain);
    }
}

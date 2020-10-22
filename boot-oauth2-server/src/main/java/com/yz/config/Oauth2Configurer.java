package com.yz.config;

import com.yz.AuthorizedGrantTypes;
import com.yz.clients.YzClientDetailsSecurityBuilder;
import com.yz.clients.YzClientDetailsService;
import com.yz.clients.repository.ClientDetailsRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * 认证服务器的配置
 *
 * @author andrew
 * @date 2020-10-21
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2Configurer extends AuthorizationServerConfigurerAdapter {

    private final ClientDetailsRepository clientDetailsRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public Oauth2Configurer(ClientDetailsRepository clientDetailsRepository,
                            AuthenticationManager authenticationManager,
                            UserDetailsService userDetailsService,
                            PasswordEncoder passwordEncoder) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 配置客户端
     *
     * @param clients 客户端配置
     * @throws Exception ex
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
//                .clients(new YzClientDetailsService(this.clientDetailsRepository))
                .inMemory()
                .withClient("yz_client")
                .secret(this.passwordEncoder.encode("yz_client_secret"))
                .resourceIds("yz_resource_ids")
                .authorizedGrantTypes(AuthorizedGrantTypes.AUTHORIZATION_CODE.toString(),
                        AuthorizedGrantTypes.REFRESH_TOKEN.toString(),
                        AuthorizedGrantTypes.IMPLICIT.toString(),
                        AuthorizedGrantTypes.PASSWORD.toString(),
                        AuthorizedGrantTypes.CLIENT_CREDENTIALS.toString())
                .refreshTokenValiditySeconds(60)
                .redirectUris("http://localhost:9000")
                .accessTokenValiditySeconds(10);
        final YzClientDetailsSecurityBuilder customBuilder = new YzClientDetailsSecurityBuilder(this.clientDetailsRepository, this.passwordEncoder);
        clients.setBuilder(customBuilder);
        clients.and().build();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(userDetailsService);
    }
}

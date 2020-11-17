package com.yz.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author andrew
 * @date 2020-11-17
 */
@Configuration
@ConditionalOnProperty(value = "spring.resource.server.origin", havingValue = "false")
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;
    private final RemoteTokenServices remoteTokenServices;
    private final AccessTokenConverter accessTokenConverter;

    public ResourceServerConfigurer(TokenStore tokenStore, RemoteTokenServices remoteTokenServices, AccessTokenConverter accessTokenConverter) {
        this.tokenStore = tokenStore;
        this.remoteTokenServices = remoteTokenServices;
        this.accessTokenConverter = accessTokenConverter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(this.tokenStore);
        this.remoteTokenServices.setAccessTokenConverter(this.accessTokenConverter);
        resources.tokenServices(this.remoteTokenServices);
    }
}

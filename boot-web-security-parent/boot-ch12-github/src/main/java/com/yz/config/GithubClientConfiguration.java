package com.yz.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * @author andrew
 * @date 2020-11-06
 */
@EnableConfigurationProperties(value = {GithubClientProperties.class})
@Configuration
public class GithubClientConfiguration {

    @Bean
    public GithubClientProperties githubClientProperties() {
        return new GithubClientProperties();
    }

    @Bean
    @Primary
    public ClientRegistration clientRegistration(GithubClientProperties githubClientProperties) {
        // create github client metadata
        return ClientRegistration.withRegistrationId(githubClientProperties.getRegistrationId())
                .clientId(githubClientProperties.getClientId())
                .clientName(githubClientProperties.getClientName())
                .clientSecret(githubClientProperties.getClientSecret())
                .scope(githubClientProperties.getScope())
                .authorizationUri(githubClientProperties.getAuthorizationUri())
                .tokenUri(githubClientProperties.getTokenUri())
                .redirectUriTemplate(githubClientProperties.getRedirectUriTemplate())
                .authorizationGrantType(new AuthorizationGrantType(githubClientProperties.getAuthorizationGrantType()))
                .userInfoUri(githubClientProperties.getUserInfoUri())
                .userNameAttributeName(githubClientProperties.getUserNameAttributeName())
                .build();
    }

    @Bean
    public ClientRegistration clientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("GITHUB")
                .clientId("7be525a88daa3a29233f")
                .clientSecret("03c474f10f689f503d7700a2439c09c9818af03e")
                .build();
    }

    @Bean
    @Primary
    public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration clientRegistration) {
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
}

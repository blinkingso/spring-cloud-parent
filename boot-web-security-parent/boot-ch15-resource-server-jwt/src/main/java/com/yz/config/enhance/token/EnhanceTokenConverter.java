package com.yz.config.enhance.token;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * @author andrew
 * @date 2020-11-17
 */
public class EnhanceTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        var authentication = super.extractAuthentication(map);
        authentication.setDetails(map);
        return authentication;
    }
}

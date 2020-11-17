package com.yz.pojo;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.time.ZoneId;
import java.util.Map;

/**
 * @author andrew
 * @date 2020-11-17
 */
public class YzTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // create a new token object based on the one we received
        var token = new DefaultOAuth2AccessToken(accessToken);
        // defines as a Map the details we want to add to the token
        Map<String, Object> userInfo = Map.of("generatedInZone", ZoneId.systemDefault().toString(),
                "renewTime", 10);
        token.setAdditionalInformation(userInfo);
        return token;
    }
}

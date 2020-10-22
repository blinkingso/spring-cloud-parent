package com.yz.clients.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author andrew
 * @date 2020-10-21
 */
@Data
@ToString
@EqualsAndHashCode
@Entity(name = "tb_client_details")
@Slf4j
public class YzClientDetails implements ClientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "scope")
    private String scopeString;
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypesString;
    @Column(name = "resource_ids")
    private String resourceIdsString;
    @Column(name = "client_secret")
    private String clientSecret;
    @Column(name = "authorities")
    private String authoritiesString;
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;
    @Column(name = "auto_approve")
    private String autoApproveString;
    @Column(name = "additional_information")
    private String additionalInformation;

    @Override
    public Set<String> getResourceIds() {
        return StringUtils.commaDelimitedListToSet(this.resourceIdsString);
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null && !this.clientSecret.isEmpty();
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return this.scopeString != null && !this.scopeString.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return StringUtils.commaDelimitedListToSet(this.scopeString);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return StringUtils.commaDelimitedListToSet(this.authorizedGrantTypesString);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return StringUtils.commaDelimitedListToSet(this.webServerRedirectUri);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(this.authoritiesString);
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (this.autoApproveString == null) {
            return false;
        }
        for (String auto : StringUtils.commaDelimitedListToStringArray(autoApproveString)) {
            if (auto.equals("true") || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        if (this.additionalInformation != null && !this.additionalInformation.isEmpty()) {
            try {
                final ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(this.additionalInformation,
                        new TypeReference<Map<String, Object>>() {
                        });
            } catch (Exception e) {
                log.warn("additionalInformation parse error", e);
            }
        }
        return Collections.emptyMap();
    }
}

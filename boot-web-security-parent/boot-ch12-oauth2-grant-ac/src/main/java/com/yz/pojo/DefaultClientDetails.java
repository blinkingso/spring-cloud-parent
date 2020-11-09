package com.yz.pojo;

import com.yz.entity.CustomClientDetails;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author andrew
 * @date 2020-11-09
 */
public class DefaultClientDetails implements ClientDetails, Serializable {

    @Getter
    private final CustomClientDetails details;

    public DefaultClientDetails(CustomClientDetails details) {
        this.details = details;
    }

    @Override
    public String getClientId() {
        return this.details.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return StringUtils.commaDelimitedListToSet(this.details.getResourceIds());
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return this.details.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return this.details.getScopes() != null && !this.details.getScopes().trim().isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return StringUtils.commaDelimitedListToSet(this.details.getScopes());
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return StringUtils.commaDelimitedListToSet(this.details.getAuthorizationGrantTypes());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return StringUtils.commaDelimitedListToSet(this.details.getRedirectUrls());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(this.details.getAuthorities());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 60;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 120;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.emptyMap();
    }
}

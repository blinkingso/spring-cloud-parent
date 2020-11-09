package com.yz.config;

import com.yz.pojo.DefaultClientDetails;
import com.yz.repository.ClientDetailsRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;

/**
 * @author andrew
 * @date 2020-11-09
 */
public final class CustomJDBCClientDetailsServiceImpl implements ClientDetailsService {

    private final ClientDetailsRepository clientDetailsRepository;

    public CustomJDBCClientDetailsServiceImpl(ClientDetailsRepository clientDetailsRepository) {
        this.clientDetailsRepository = clientDetailsRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return this.clientDetailsRepository.findCustomClientDetailsByClientId(clientId)
                .map(DefaultClientDetails::new)
                .orElseThrow(() -> new NoSuchClientException("client details not found"));
    }
}

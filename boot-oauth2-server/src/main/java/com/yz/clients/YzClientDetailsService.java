package com.yz.clients;

import com.yz.clients.repository.ClientDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * @author andrew
 * @date 2020-10-21
 */
public class YzClientDetailsService implements ClientDetailsService {

    private final ClientDetailsRepository clientDetailsRepository;

    public YzClientDetailsService(ClientDetailsRepository clientDetailsRepository, PasswordEncoder passwordEncoder) {
        this.clientDetailsRepository = clientDetailsRepository;
    }


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return this.clientDetailsRepository.findYzClientDetailsByClientId(clientId).map(s -> {
                    // BaseClientDetails
                    return new BaseClientDetails();
                }
        ).orElseThrow(() -> new ClientRegistrationException("未找到配置的客户端"));
    }
}

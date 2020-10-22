package com.yz.clients;

import com.yz.clients.pojo.YzClientDetails;
import com.yz.clients.repository.ClientDetailsRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import java.util.List;

/**
 * @author andrew
 * @date 2020-10-21
 */
public class YzClientDetailsService implements ClientDetailsService {

    private final ClientDetailsRepository clientDetailsRepository;

    public YzClientDetailsService(ClientDetailsRepository clientDetailsRepository) {
        this.clientDetailsRepository = clientDetailsRepository;
    }

    public List<YzClientDetails> addClients(List<YzClientDetails> clients) {
        return this.clientDetailsRepository.saveAll(clients);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return this.clientDetailsRepository.findYzClientDetailsByClientId(clientId)
                .orElseThrow(() -> new ClientRegistrationException("未找到配置的客户端"));
    }
}

package com.yz.clients;

import com.yz.clients.pojo.YzClientDetails;
import com.yz.clients.repository.ClientDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.util.Assert;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author andrew
 * @date 2020-10-22
 */
public class YzClientDetailsSecurityBuilder extends ClientDetailsServiceBuilder<YzClientDetailsSecurityBuilder> {

    private Set<YzClientDetails> addedClients = new LinkedHashSet<>();

    private final ClientDetailsRepository clientDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public YzClientDetailsSecurityBuilder(ClientDetailsRepository clientDetailsRepository,
                                          PasswordEncoder passwordEncoder) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void addClient(String clientId, ClientDetails build) {
        if (build instanceof YzClientDetails) {
            addedClients.add((YzClientDetails) build);
        } else {
            super.addClient(clientId, build);
        }
    }

    @Override
    protected ClientDetailsService performBuild() {
        Assert.notNull(this.clientDetailsRepository, "clientDetailsRepository can not be null");
        Assert.notNull(this.passwordEncoder, "passwordEncoder can not be null");
        YzClientDetailsService service = new YzClientDetailsService(this.clientDetailsRepository);
        if (!this.addedClients.isEmpty()) {
            // 初始化数据到db中
            final List<YzClientDetails> list = this.addedClients.stream().peek(c -> c.setClientSecret(passwordEncoder.encode(c.getClientSecret()))).collect(Collectors.toList());
            service.addClients(list);
        }

        return service;
    }
}

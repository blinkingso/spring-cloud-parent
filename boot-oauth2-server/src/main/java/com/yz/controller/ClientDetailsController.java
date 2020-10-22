package com.yz.controller;

import com.yz.clients.pojo.YzClientDetails;
import com.yz.clients.repository.ClientDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author andrew
 * @date 2020-10-22
 */
@RestController
@RequestMapping("/clients")
@Slf4j
public class ClientDetailsController {

    private final ClientDetailsRepository clientDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientDetailsController(ClientDetailsRepository clientDetailsRepository, PasswordEncoder passwordEncoder) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{clientId}")
    public YzClientDetails clientDetails(@PathVariable String clientId) {
        return clientDetailsRepository.findYzClientDetailsByClientId(clientId).orElse(null);
    }

    @GetMapping
    public List<YzClientDetails> listClientDetails() {
        return clientDetailsRepository.findAll();
    }

    @PutMapping
    public YzClientDetails save(@RequestBody YzClientDetails yzClientDetails) {
        yzClientDetails.setClientSecret(this.passwordEncoder.encode(yzClientDetails.getClientSecret()));
        return clientDetailsRepository.save(yzClientDetails);
    }

    @PostMapping
    public YzClientDetails update(@RequestBody YzClientDetails yzClientDetails) {
        YzClientDetails details = this.clientDetailsRepository.findYzClientDetailsByClientId(yzClientDetails.getClientId())
                .orElse(null);
        if (null == details) {
            return null;
        }

        BeanUtils.copyProperties(yzClientDetails, details, "id");
        details.setClientSecret(this.passwordEncoder.encode(details.getClientSecret()));

        // 更新
        return this.clientDetailsRepository.save(details);
    }

    @DeleteMapping("/{clientId}")
    public String delete(@PathVariable String clientId) {
        this.clientDetailsRepository.deleteYzClientDetailsByClientId(clientId);
        return "delete success with clientId : " + clientId;
    }
}

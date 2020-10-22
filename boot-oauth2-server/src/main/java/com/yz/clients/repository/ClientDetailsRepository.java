package com.yz.clients.repository;

import com.yz.clients.pojo.YzClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author andrew
 * @date 2020-10-21
 */
public interface ClientDetailsRepository extends JpaRepository<YzClientDetails, Long> {

    Optional<YzClientDetails> findYzClientDetailsByClientId(String clientId);

    void deleteYzClientDetailsByClientId(String clientId);
}

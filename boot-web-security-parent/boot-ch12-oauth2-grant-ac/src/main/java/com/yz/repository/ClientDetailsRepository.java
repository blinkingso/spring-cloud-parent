package com.yz.repository;

import com.yz.entity.CustomClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author andrew
 * @date 2020-11-09
 */
@Repository
public interface ClientDetailsRepository extends JpaRepository<CustomClientDetails, Long> {

    Optional<CustomClientDetails> findCustomClientDetailsByClientId(String clientId);
}

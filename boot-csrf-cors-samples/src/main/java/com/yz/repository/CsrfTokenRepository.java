package com.yz.repository;

import com.yz.pojo.CsrfToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author andrew
 * @date 2020-10-21
 */
@Repository
public interface CsrfTokenRepository extends JpaRepository<CsrfToken, Long> {

    Optional<CsrfToken> findCsrfTokenByIdentifier(String identifier);
}

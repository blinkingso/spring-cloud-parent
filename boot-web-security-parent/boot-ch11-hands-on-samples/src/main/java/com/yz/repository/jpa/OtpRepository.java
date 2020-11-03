package com.yz.repository.jpa;

import com.yz.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author andrew
 * @date 2020-11-03
 */
@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findOtpByUsername(String username);
}

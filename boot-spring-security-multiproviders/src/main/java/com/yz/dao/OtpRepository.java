package com.yz.dao;

import com.yz.pojo.Otp;
import com.yz.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author andrew
 * @date 2020-10-15
 */
@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    /**
     * 通过username查询Otp
     *
     * @param username
     * @return
     */
    Optional<List<Otp>> findAllByUsername(String username);
}

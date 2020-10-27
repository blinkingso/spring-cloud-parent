package com.yz.repository;

import com.yz.userdetails.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author andrew
 * @date 2020-10-27
 */
@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}

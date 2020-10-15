package com.yz.dao;

import com.yz.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author andrew
 * @date 2020-10-15
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return user
     */
    Optional<User> findUserByUsername(String username);
}

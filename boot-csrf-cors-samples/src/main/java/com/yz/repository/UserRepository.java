package com.yz.repository;

import com.yz.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author andrew
 * @date 2020-10-24
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // find user by username return optional object
    Optional<User> findUserByUsername(String username);

    // delete a user via specified username
    void deleteUserByUsername(String username);
}

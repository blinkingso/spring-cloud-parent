package com.yz.service;

import com.yz.pojo.User;

import java.util.List;

/**
 * yz user details service operation apis
 *
 * @author andrew
 * @date 2020-10-24
 */
public interface YzUserDetailsService {

    // list all users
    List<User> users();

    // find user via username
    User findUserByUsername(String username);
}

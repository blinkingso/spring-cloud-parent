package com.yz.controller;

import com.yz.pojo.SecurityUser;
import com.yz.pojo.User;
import com.yz.service.impl.YzUserDetailsServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * user controller
 *
 * @author andrew
 * @date 2020-10-24
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Lazy
public class UserController {

    private final YzUserDetailsServiceManager yzUserDetailsServiceManager;

    @Autowired
    public UserController(@Qualifier("userDetailsService") YzUserDetailsServiceManager yzUserDetailsServiceManager) {
        this.yzUserDetailsServiceManager = yzUserDetailsServiceManager;
    }

    @GetMapping({"", "/"})
    public List<User> users() {
        return this.yzUserDetailsServiceManager.users();
    }

    @GetMapping("/{username}")
    public User user(@PathVariable String username) {
        return this.yzUserDetailsServiceManager.findUserByUsername(username);
    }

    @PutMapping
    public User save(@RequestBody User user) {
        this.yzUserDetailsServiceManager.createUser(new SecurityUser(user));
        log.info("save success");
        return user;
    }

    @PostMapping
    public User update(@RequestBody User user) {
        this.yzUserDetailsServiceManager.updateUser(new SecurityUser(user));
        log.info("update success");
        return user;
    }

    @DeleteMapping("/{username}")
    public String deleteByUsername(@PathVariable String username) {
        this.yzUserDetailsServiceManager.deleteUser(username);
        log.info("delete " + username + " success");
        return "delete success";
    }
}

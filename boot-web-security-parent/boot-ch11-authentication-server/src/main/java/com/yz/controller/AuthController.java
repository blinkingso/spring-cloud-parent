package com.yz.controller;

import com.yz.entity.Otp;
import com.yz.entity.User;
import com.yz.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrew
 * @date 2020-11-04
 */
@RestController
@RequestMapping
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/user/auth")
    public boolean auth(@RequestBody User user) {
        return userService.auth(user);
    }

    @PostMapping("/otp/check")
    public boolean check(@RequestBody Otp otp) {
        return this.userService.check(otp);
    }
}

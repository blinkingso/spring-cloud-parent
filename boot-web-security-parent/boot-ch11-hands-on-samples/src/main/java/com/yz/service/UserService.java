package com.yz.service;

import com.yz.entity.Otp;
import com.yz.entity.User;
import com.yz.repository.jpa.OtpRepository;
import com.yz.repository.jpa.UserRepository;
import com.yz.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author andrew
 * @date 2020-11-03
 */
@Slf4j
@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpRepository otpRepository;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean auth(User user) {
        // if success than create an otp code
        final Optional<User> option = userRepository.findUserByUsername(user.getUsername());
        if (option.isPresent()) {
            // check password
            var u = option.get();
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                // renew an Otp code
                renewOtp(u);
                return true;
            } else {
                log.info("user " + user.getUsername() + " password auth failed");
                return false;
            }
        }
        log.info("user " + user.getUsername() + " is not present");
        return false;
    }

    private void renewOtp(User user) {
        var code = GenerateCodeUtil.generateCode();
        var otp = otpRepository.findOtpByUsername(user.getUsername());
        Otp o;
        if (otp.isPresent()) {
            o = otp.get();
        } else {
            o = new Otp();
            o.setUsername(user.getUsername());
        }
        o.setCode(code);
        otpRepository.save(o);
    }
}

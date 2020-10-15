package com.yz;

import com.yz.config.ProjectConfig;
import com.yz.config.WebSecurityConfig;
import com.yz.dao.UserRepository;
import com.yz.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author andrew
 * @date 2020-10-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebSecurityConfig.class, ProjectConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class Test_Password {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUser() {
        User user = new User(0L, "admin", passwordEncoder.encode("admin"));
        final User saved = userRepository.save(user);
        log.info("保存的用户为: " + saved);
    }
}

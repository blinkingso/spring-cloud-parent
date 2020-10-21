package com.yz;

import com.yz.clients.pojo.YzClientDetails;
import com.yz.clients.repository.ClientDetailsRepository;
import com.yz.config.Oauth2Configurer;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author andrew
 * @date 2020-10-21
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Test_Clients {

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Test
    public void sel() {
        final List<YzClientDetails> all = clientDetailsRepository.findAll();
        all.forEach(System.out::println);
        System.out.println("hello");
    }

    @Test
    public void testJson() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(new BaseClientDetails()));
    }
}

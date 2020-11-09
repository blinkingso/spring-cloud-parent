package com.yz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Oauth 2 grants type : Authorization code, password, refresh code, client credentials
 * 1. authorization code flows:
 * User                         Client               Authorization Server                Resource Server
 * User -> Client : I want to access my accounts
 *
 * User <- Client:  Tell the authorization server you allow me to do this action.
 *
 * User -> Authorization Server :  Hey, authorization server! I allow the client to access my accounts. Here are my
 * credentials  to prove who I  am.
 *
 * Client <- Authorization Server: Hi Client. Katushka allowed you to access her accounts.
 *
 *  Client -> Authorization Server: Perfect! Give me a token to access her accounts.
 *
 * Client <- Authorization Server: Here is a token.
 *
 * Client -> Resource Server: Hi resource server. I want to access Katushka's accounts. Here is a token from the
 * authorization server.
 *
 * @author andrew
 * @date 2020-11-05
 */
@SpringBootApplication
public class App_Ch12_Grants9090 {

    public static void main(String[] args) {
        SpringApplication.run(App_Ch12_Grants9090.class, args);
    }
}

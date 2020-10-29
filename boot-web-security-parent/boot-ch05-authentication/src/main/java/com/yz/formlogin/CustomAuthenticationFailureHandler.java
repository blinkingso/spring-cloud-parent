package com.yz.formlogin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * customize a failure handler to process logic where A Request Authentication Failed
 *
 * @author andrew
 * @date 2020-10-29
 */
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // set error header
        response.setHeader("FAILED", LocalDateTime.now().toString());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("text/html");
        response.getWriter().write("AUTH FAILED");
    }
}

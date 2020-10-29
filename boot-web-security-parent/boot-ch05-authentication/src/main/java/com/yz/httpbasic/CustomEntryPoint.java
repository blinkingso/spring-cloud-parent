package com.yz.httpbasic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Its commence() method receives the HttpServlet- Request, the HttpServletResponse,
 * and the AuthenticationException that cause the authentication to fail
 *
 * 认证接收到AuthenticationException时的处理切入点
 *
 * @author andrew
 * @date 2020-10-29
 */
@Slf4j
public class CustomEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // get an exception
        log.info("now we are at here !!! Amazing ???  HaHa  ^_--_^");
        response.addHeader("message", "This request auth failed");
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "NI MEI YOU TONG GUO KAO YAN");
    }
}

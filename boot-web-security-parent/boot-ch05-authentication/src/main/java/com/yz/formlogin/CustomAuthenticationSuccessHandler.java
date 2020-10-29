package com.yz.formlogin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * customize a process logic when Request Authentication is Successful
 *
 * @author andrew
 * @date 2020-10-29
 */
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        var authorities = authentication.getAuthorities();
        var auth = authorities.stream()
                .filter(a -> a.getAuthority().equalsIgnoreCase("write"))
                .findFirst();
        if (auth.isPresent()) {
            log.info("congratulations, you " + authentication.getName() + " are accessed in.");
            response.sendRedirect("/home");
        } else {
            log.warn("sorry [" + authentication.getName() + "], you have no authority to access, you are redirected " +
                    "to the error page");
            response.sendRedirect("/error");
        }
    }
}

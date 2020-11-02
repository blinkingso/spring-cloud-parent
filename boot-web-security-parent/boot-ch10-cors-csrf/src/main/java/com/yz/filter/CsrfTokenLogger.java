package com.yz.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author andrew
 * @date 2020-11-02
 */
@Slf4j
public class CsrfTokenLogger implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final Object object = request.getAttribute("_csrf");
        if (object != null) {
            var token = (CsrfToken) object;
            log.info("CSRF TOKEN " + token.getToken());
        }

        chain.doFilter(request, response);
    }
}

package com.yz.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author andrew
 * @date 2020-10-20
 */
@Slf4j
public class YzCsrfTokenLoggerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String csrfToken = request.getHeader("X-CSRF-TOKEN");
        log.info("csrf token的结果为: " + csrfToken);
        filterChain.doFilter(request, response);
    }
}

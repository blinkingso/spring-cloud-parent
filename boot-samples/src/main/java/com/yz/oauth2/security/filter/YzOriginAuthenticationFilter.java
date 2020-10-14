package com.yz.oauth2.security.filter;

import com.yz.oauth2.security.authentication.YzRemoteOriginAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author andrew
 * @date 2020-10-14
 */
@Slf4j
@Component
public class YzOriginAuthenticationFilter implements Filter {

    private final AuthenticationManager authenticationManager;

    public YzOriginAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("开始执行自定义过滤器");
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader("host");
        YzRemoteOriginAuthentication authentication = new YzRemoteOriginAuthentication(header, null);
        try {
            final Authentication res = authenticationManager.authenticate(authentication);
            if (res.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(res);
                chain.doFilter(request, response);
            } else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("认证失败");
                response.getWriter().flush();
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            final HttpServletResponse resp = (HttpServletResponse) response;
            if (e.getClass().isAssignableFrom(AuthenticationException.class)) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } finally {
            log.info("自定义过滤器执行结束");
        }
    }
}

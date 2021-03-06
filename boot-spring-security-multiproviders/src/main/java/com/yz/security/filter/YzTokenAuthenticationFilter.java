package com.yz.security.filter;

import com.yz.pojo.authentication.TokenAuthentication;
import com.yz.security.cache.TokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author andrew
 * @date 2020-10-15
 */
@Slf4j
public class YzTokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public YzTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TokenCache.HEADER_AUTHORIZATION);
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 初始化TokenAuthentication并保存到cache中
        try {
            TokenAuthentication tokenAuthentication = new TokenAuthentication(token, null, null);
            Authentication authentication = authenticationManager.authenticate(tokenAuthentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            logger.info("认证失败");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.TEXT_HTML_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("token认证不通过");
        }
    }

    /**
     * token为空或者otp认证时不需要执行this filter的logic
     *
     * @param request httpRequest
     * @return 是否不需要进行过滤
     * @throws ServletException servlet exception
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }
}

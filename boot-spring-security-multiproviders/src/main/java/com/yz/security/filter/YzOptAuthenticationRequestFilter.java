package com.yz.security.filter;

import com.yz.dao.OtpRepository;
import com.yz.pojo.Otp;
import com.yz.pojo.authentication.OtpAuthentication;
import com.yz.pojo.authentication.UsernamePasswordAuthentication;
import com.yz.security.cache.TokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * 自定义过滤器
 * <p>
 * mock 多 AuthenticationProvider
 *
 * @author andrew
 * @date 2020-10-15
 */
@Slf4j
public class YzOptAuthenticationRequestFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final OtpRepository otpRepository;
    private final TokenCache tokenCache;

    public YzOptAuthenticationRequestFilter(AuthenticationManager authenticationManager, OtpRepository otpRepository, TokenCache tokenCache) {
        this.authenticationManager = authenticationManager;
        this.otpRepository = otpRepository;
        this.tokenCache = tokenCache;
    }

    /**
     * 过滤除/login的所有request
     *
     * @param request 请求
     * @return 是否需要过滤
     * @throws ServletException 异常
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // 1. 读取header信息
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");
        Authentication authentication;
        if (!StringUtils.isEmpty(otp)) {
            // 密码模式
            authentication = new UsernamePasswordAuthentication(username, password);
            String code = String.valueOf(new Random().nextInt(9999) + 1000);
            Otp ot = new Otp();
            ot.setUsername(username);
            ot.setOtp(code);

            try {
                otpRepository.save(ot);
            } catch (Exception e) {
                log.error("sql 执行失败: ", e);
                throw new AuthenticationServiceException("otp保存失败");
            }
            authentication = authenticationManager.authenticate(authentication);
        } else {
            // 一次性密码模式
            authentication = new OtpAuthentication(username, password);
            authentication = authenticationManager.authenticate(authentication);
            final String token = TokenCache.createToken();
            response.setHeader(TokenCache.HEADER_AUTHORIZATION, token);
            tokenCache.put(token);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}

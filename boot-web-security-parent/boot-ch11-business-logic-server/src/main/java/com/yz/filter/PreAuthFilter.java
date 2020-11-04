package com.yz.filter;

import com.yz.authentication.OtpAuthentication;
import com.yz.authentication.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * PreAuth and Generate JWT
 * @author andrew
 * @date 2020-11-04
 */
public class PreAuthFilter extends OncePerRequestFilter {

    private final String signKey;
    private final AuthenticationManager authenticationManager;

    public PreAuthFilter(String signKey, AuthenticationManager authenticationManager) {
        this.signKey = signKey;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");
        if (null == code) {
            // username password authenticate
            Authentication authentication = new UsernamePasswordAuthentication(username, password);
            authenticationManager.authenticate(authentication);
        } else {
            Authentication authentication = new OtpAuthentication(username, code);
            authentication = authenticationManager.authenticate(authentication);

            // JSON WEB TOKEN GENERATE
            var key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(this.signKey));
            var jwt = Jwts.builder().setClaims(Map.of("username", username))
                    .signWith(key)
                    .compact();
            response.setHeader("Authorization", jwt);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // only filter /login request
        return !request.getServletPath().equals("/login");
    }
}

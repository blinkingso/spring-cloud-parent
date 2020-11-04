package com.yz.filter;

import com.yz.authentication.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * @author andrew
 * @date 2020-11-04
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String signKey;

    public JwtAuthenticationFilter(String signKey) {
        this.signKey = signKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        var key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(signKey));
        var claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(jwt).getBody();
        String username = String.valueOf(claims.get("username"));
        var auth = new UsernamePasswordAuthentication(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }
}

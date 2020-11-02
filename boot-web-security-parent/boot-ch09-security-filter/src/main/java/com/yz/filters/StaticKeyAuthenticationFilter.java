package com.yz.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author andrew
 * @date 2020-11-01
 */
public class StaticKeyAuthenticationFilter extends AbstractRequestHeaderAuthenticationFilter {

    public StaticKeyAuthenticationFilter() {
        var cache = new HashMap<String, String>();
        cache.put("Request-Id", "rid");
        cache.put("Access-Key", "ak");
        super.defaultStrategy(cache);
    }

    @Override
    protected List<String> getHeaderNames() {
        return List.of("Request-Id", "Access-Key");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;
        super.doInternalFilter(req, res, chain);
    }
}

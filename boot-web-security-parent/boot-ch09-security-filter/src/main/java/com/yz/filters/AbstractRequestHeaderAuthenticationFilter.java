package com.yz.filters;

import com.yz.filters.validation.DefaultValidationHeadersStrategy;
import com.yz.filters.validation.ValidateHeadersStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * abstract filter to check header
 *
 * @author andrew
 * @date 2020-11-01
 */
@Slf4j
public abstract class AbstractRequestHeaderAuthenticationFilter implements Filter {

    private ValidateHeadersStrategy strategy;

    protected void defaultStrategy(Map<String, String> headers) {
        this.strategy = new DefaultValidationHeadersStrategy(headers);
    }

    protected void setStrategy(ValidateHeadersStrategy strategy) {
        this.strategy = strategy;
    }

    // get all header
    protected abstract List<String> getHeaderNames();

    protected void doInternalFilter(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        Assert.notNull(this.strategy, "headers validation strategy can not be null");
        final Map<String, String> headersMap = getHeaderNames().stream().collect(
                Collectors.toMap(it -> it,
                        it -> {
                            if (request.getHeader(it) == null) {
                                return "";
                            } return request.getHeader(it);
                        })
        );
        if (this.strategy.validate(headersMap)) {
            // 验证 通过
            chain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            log.error("Http Header validation failed....");
        }
    }
}

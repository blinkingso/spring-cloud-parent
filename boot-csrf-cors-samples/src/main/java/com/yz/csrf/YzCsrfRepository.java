package com.yz.csrf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author andrew
 * @date 2020-10-20
 */
@Slf4j
public class YzCsrfRepository implements CsrfTokenRepository {

    private final CsrfTokenRepository delegate;

    public YzCsrfRepository(CsrfTokenRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        final CsrfToken csrfToken = this.delegate.generateToken(request);
        log.info("CsrfToken生成token为: " + csrfToken.getToken());
        return csrfToken;
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        this.delegate.saveToken(token, request, response);
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return this.delegate.loadToken(request);
    }
}

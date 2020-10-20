package com.yz.csrf;

import com.yz.security.cache.TokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义CsrfToken存储加载的logic
 *
 * @author andrew
 * @date 2020-10-20
 */
@Slf4j
public class YzCsrfTokenRepository implements CsrfTokenRepository {

    private final CsrfTokenRepository csrfTokenRepository;

    public YzCsrfTokenRepository(CsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        final String token = TokenCache.createToken();
        log.info("CsrfToken创建成功::::" + token);
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        this.csrfTokenRepository.saveToken(token, request, response);
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return this.csrfTokenRepository.loadToken(request);
    }
}

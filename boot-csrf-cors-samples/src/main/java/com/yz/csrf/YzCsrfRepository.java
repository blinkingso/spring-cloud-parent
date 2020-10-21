package com.yz.csrf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

/**
 * @author andrew
 * @date 2020-10-20
 */
@Slf4j
public class YzCsrfRepository implements CsrfTokenRepository {

    public static final String X_IDENTIFIER_HEADER_NAME = "X-IDENTIFIER";

    private final com.yz.repository.CsrfTokenRepository repository;

    public YzCsrfRepository(com.yz.repository.CsrfTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        final CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token);
        log.info("CsrfToken生成token为: " + csrfToken.getToken());
        return csrfToken;
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        final String identifier = request.getHeader(X_IDENTIFIER_HEADER_NAME);
        if (StringUtils.isEmpty(identifier) || token == null) {
            log.warn("请求头==>" + X_IDENTIFIER_HEADER_NAME + "is empty,  or token is null");
            return;
        }
        Optional<com.yz.pojo.CsrfToken> existingToken = this.repository.findCsrfTokenByIdentifier(identifier);
        // 将token保存到db中
        if (existingToken.isPresent()) {
            com.yz.pojo.CsrfToken t = existingToken.get();
            t.setToken(token.getToken());
            log.info("identifier为: " + identifier + "==>之前的token为: " + t.getToken() + ", 现在的token为: " + token.getToken());
        } else {
            final com.yz.pojo.CsrfToken t = new com.yz.pojo.CsrfToken();
            t.setIdentifier(identifier);
            t.setToken(token.getToken());
            this.repository.save(t);
            log.info("identifier为: " + identifier + "==>保存的token为: " + t.getToken());
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String identifier = request.getHeader(X_IDENTIFIER_HEADER_NAME);
        if (StringUtils.isEmpty(identifier)) {
            log.warn("未加载到CsrfToken, 请求头" + X_IDENTIFIER_HEADER_NAME + "为空");
            return null;
        }
        final Optional<com.yz.pojo.CsrfToken> csrfTokenByIdentifier = this.repository.findCsrfTokenByIdentifier(identifier);
        return csrfTokenByIdentifier.map(s ->
                new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", s.getToken()))
                .orElse(null);
    }
}

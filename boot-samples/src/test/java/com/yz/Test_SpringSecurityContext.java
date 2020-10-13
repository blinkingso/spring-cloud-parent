package java.com.yz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * @author andrew
 * @date 2020-10-13
 */
@Slf4j
public class Test_SpringSecurityContext {

    /**
     * SecurityContextHolder is where Spring Security stores the details of who is authenticated
     */
    @Test
    public void test_SecurityContextHolder() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication testingAuthenticationToken = new TestingAuthenticationToken("admin", "admin", "ROLE_USER");
        securityContext.setAuthentication(testingAuthenticationToken);
        SecurityContextHolder.setContext(securityContext);

        // Access Currently Authenticated User 获取当前线程认证后的用户信息
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // 用户认证主体, 用户名等
        final Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 获取认证权限
        final Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        authorities.forEach(s -> System.out.println(s.getAuthority()));
        log.info("认证主体为: " + principle + ", 用户名为: " + username);
    }
}

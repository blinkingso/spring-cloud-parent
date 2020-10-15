package com.yz.security.providers;

import com.yz.dao.OtpRepository;
import com.yz.pojo.Otp;
import com.yz.pojo.authentication.OtpAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author andrew
 * @date 2020-10-15
 */
@Slf4j
public class YzOtpAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final OtpRepository otpRepository;

    public YzOtpAuthenticationProvider(UserDetailsService userDetailsService, OtpRepository otpRepository) {
        this.userDetailsService = userDetailsService;
        this.otpRepository = otpRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String inputOtp = (String) authentication.getCredentials();
        Optional<List<Otp>> otp = otpRepository.findAllByUsername(username);
        if (otp.isPresent() && !CollectionUtils.isEmpty(otp.get())) {
            return new OtpAuthentication(username, inputOtp);
        }

        throw new BadCredentialsException("OTP验证失败");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}

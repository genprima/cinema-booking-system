package com.gen.cinema.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.gen.cinema.domain.User;
import com.gen.cinema.domain.UserOtp;
import com.gen.cinema.repository.UserOtpRepository;
import com.gen.cinema.repository.UserRepository;
import com.gen.cinema.security.authentication.OtpAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        OtpAuthenticationToken authToken = (OtpAuthenticationToken) authentication;
        String email = authToken.getEmail();
        String otp = (String) authToken.getCredentials();

        log.debug("Authenticating OTP for email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or OTP"));


        UserOtp userOtp = userOtpRepository.findLatestActiveOtp(user.getId(), Instant.now())
                .orElseThrow(() -> new BadCredentialsException("No active OTP found"));

        if (!userOtp.getOtp().equals(otp)) {
            throw new BadCredentialsException("Invalid OTP");
        }

        if (userOtp.isExpired()) {
            throw new BadCredentialsException("OTP has expired");
        }

        if (!userOtp.isUsed()) {
            userOtp.setUsed(true);
            userOtpRepository.save(userOtp);
        }

        log.debug("OTP authentication successful for user: {}", email);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        return new OtpAuthenticationToken(user, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthenticationToken.class.isAssignableFrom(authentication);
    }
} 
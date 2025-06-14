package com.gen.cinema.security.provider;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.gen.cinema.domain.User;
import com.gen.cinema.domain.UserOtp;
import com.gen.cinema.repository.UserRepository;
import com.gen.cinema.repository.UserOtpRepository;
import com.gen.cinema.security.authentication.OtpAuthenticationToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;

    public OtpAuthenticationProvider(
            UserRepository userRepository,
            UserOtpRepository userOtpRepository) {
        this.userRepository = userRepository;
        this.userOtpRepository = userOtpRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OtpAuthenticationToken token = (OtpAuthenticationToken) authentication;
        String email = token.getEmail();
        String otp = token.getOtp();

        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        // Find active OTP for user
        UserOtp userOtp = userOtpRepository.findActiveOtp(user.getId(), otp, Instant.now())
                .orElseThrow(() -> new BadCredentialsException("Invalid or expired OTP"));

        // Mark OTP as used
        userOtp.setUsed(true);
        userOtpRepository.save(userOtp);

        // Return authenticated token
        return new OtpAuthenticationToken(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthenticationToken.class.isAssignableFrom(authentication);
    }
} 
package com.gen.cinema.security.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import com.gen.cinema.domain.User;
import com.gen.cinema.domain.UserOtp;
import com.gen.cinema.repository.UserRepository;
import com.gen.cinema.repository.UserOtpRepository;
import com.gen.cinema.security.authentication.EmailAuthenticationToken;
import com.gen.cinema.service.EmailService;

@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;
    private static final int OTP_EXPIRY_MINUTES = 5;

    public EmailAuthenticationProvider(
            UserRepository userRepository,
            UserOtpRepository userOtpRepository,
            EmailService emailService) {
        this.userRepository = userRepository;
        this.userOtpRepository = userOtpRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailAuthenticationToken token = (EmailAuthenticationToken) authentication;
        String email = token.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        String otp = generateOTP();
        Instant expiresAt = Instant.now().plus(OTP_EXPIRY_MINUTES, ChronoUnit.MINUTES);

        // Store OTP in database
        UserOtp userOtp = new UserOtp();
        userOtp.setUser(user);
        userOtp.setOtp(otp);
        userOtp.setExpiresAt(expiresAt);
        userOtpRepository.save(userOtp);

        // Return unauthenticated token
        return new EmailAuthenticationToken(user, email, otp);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String generateOTP() {
        // Generate a 6-digit OTP
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}
package com.gen.cinema.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gen.cinema.domain.User;
import com.gen.cinema.domain.UserOtp;
import com.gen.cinema.repository.UserOtpRepository;
import com.gen.cinema.repository.UserRepository;
import com.gen.cinema.security.authentication.OtpAuthenticationToken;
import com.gen.cinema.service.SessionService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(OtpAuthenticationProvider.class);

    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;
    private final SessionService sessionService;

    public OtpAuthenticationProvider(UserRepository userRepository, UserOtpRepository userOtpRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.userOtpRepository = userOtpRepository;
        this.sessionService = sessionService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OtpAuthenticationToken authToken = (OtpAuthenticationToken) authentication;
        String otp = (String) authToken.getCredentials();
        String sessionId = authToken.getSessionId();

        log.debug("Authenticating OTP with session: {}", sessionId);

        // Validate session and extract email
        if (sessionId == null || sessionId.isEmpty()) {
            log.warn("Session ID is missing or empty");
            throw new BadCredentialsException("Session ID is required");
        }

        log.debug("Retrieving session data for sessionId: {}", sessionId);
        Optional<String> sessionEmail = sessionService.getSessionData(sessionId);
        
        if (sessionEmail.isEmpty()) {
            log.warn("Invalid or expired session: {}", sessionId);
            throw new BadCredentialsException("Invalid or expired session. Please login again.");
        }

        String email = sessionEmail.get();
        log.debug("Session validated successfully for email: {}", email);
        
        // Update last accessed time in a separate transaction
        sessionService.updateLastAccessedTime(sessionId);

        // Validate user exists
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found for email: {}", email);
                    return new BadCredentialsException("Invalid email or OTP");
                });

        log.debug("User found: {} with role: {}", email, user.getRole());

        // Validate OTP
        UserOtp userOtp = userOtpRepository.findLatestActiveOtp(user.getId(), Instant.now())
                .orElseThrow(() -> {
                    log.warn("No active OTP found for user: {}", email);
                    return new BadCredentialsException("No active OTP found. Please request a new OTP.");
                });

        log.debug("Found active OTP for user: {}", email);

        if (!userOtp.getOtp().equals(otp)) {
            log.warn("Invalid OTP provided for user: {}", email);
            throw new BadCredentialsException("Invalid OTP");
        }

        if (userOtp.isExpired()) {
            log.warn("OTP has expired for user: {}", email);
            throw new BadCredentialsException("OTP has expired. Please request a new OTP.");
        }

        if (!userOtp.isUsed()) {
            userOtp.setUsed(true);
            userOtpRepository.save(userOtp);
            log.debug("Marked OTP as used for user: {}", email);
        }

        log.debug("Cleaning up session after successful authentication: {}", sessionId);
        sessionService.deleteSession(sessionId);

        log.info("OTP authentication successful for user: {}", email);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return new OtpAuthenticationToken(user, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
package com.gen.cinema.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.cinema.exception.BadRequestAlertException;
import com.gen.cinema.security.authentication.EmailAuthenticationToken;
import com.gen.cinema.service.EmailService;
import com.gen.cinema.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(EmailAuthenticationSuccessHandler.class);
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final SessionService sessionService;
    private static final int OTP_EXPIRY_MINUTES = 100;

    public EmailAuthenticationSuccessHandler(ObjectMapper objectMapper, EmailService emailService, SessionService sessionService) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.sessionService = sessionService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        EmailAuthenticationToken authToken = (EmailAuthenticationToken) authentication;
        String email = authToken.getEmail();
        String otp = authToken.getOtp();
        
        // Store email in database session
        String sessionId = sessionService.createSession(email, email, OTP_EXPIRY_MINUTES);
        log.debug("Stored email in database session: {} with session ID: {}", email, sessionId);

        try {
            log.info("Sending OTP to user {}: {}", email, otp);
            emailService.sendMail("Your OTP is: " + otp + ". It will expire in " + OTP_EXPIRY_MINUTES + " minutes.");
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new BadRequestAlertException("Failed to send OTP email");
        }

        Map<String, String> resultmap = new HashMap<>();
        resultmap.put("status", "success");
        resultmap.put("message", "OTP has been sent to your email");
        resultmap.put("sessionId", sessionId);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), resultmap);
    }
} 
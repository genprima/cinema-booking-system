package com.gen.cinema.security.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.cinema.dto.request.OtpVerificationRequest;
import com.gen.cinema.security.authentication.OtpAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OtpAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private static final String OTP_VERIFY_URL = "/v1/auth/verify-otp";

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    public OtpAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(OTP_VERIFY_URL, "POST"));
        this.objectMapper = objectMapper;
        this.setAuthenticationManager(authenticationManager);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            String requestBody = request.getReader().lines().collect(Collectors.joining());
            log.debug("Received OTP verification request body: [{}]", requestBody);

            if (requestBody == null || requestBody.trim().isEmpty()) {
                log.error("Empty request body received");
                throw new BadCredentialsException("Request body is empty");
            }

            OtpVerificationRequest verificationRequest = objectMapper.readValue(requestBody, OtpVerificationRequest.class);
            log.debug("Parsed OTP verification request: {}", verificationRequest);

            if (verificationRequest.getEmail() == null || verificationRequest.getEmail().trim().isEmpty()) {
                throw new BadCredentialsException("Email is required");
            }

            if (verificationRequest.getOtp() == null || verificationRequest.getOtp().trim().isEmpty()) {
                throw new BadCredentialsException("OTP is required");
            }

            OtpAuthenticationToken authRequest = new OtpAuthenticationToken(
                verificationRequest.getEmail().trim(),
                verificationRequest.getOtp().trim()
            );
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            log.error("Error processing authentication request", e);
            throw new BadCredentialsException("Invalid request format");
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        
        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }
    
} 
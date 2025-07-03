package com.gen.cinema.security.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

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
import com.gen.cinema.service.SessionService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtpAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(OtpAuthenticationFilter.class);
    private final ObjectMapper objectMapper;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final SessionService sessionService;

    public OtpAuthenticationFilter(
            String defaultFilterProcessesUrl,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper,
            SessionService sessionService) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.sessionService = sessionService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            BufferedReader reader = request.getReader();
            String requestBody = reader.lines().collect(Collectors.joining());

            OtpVerificationRequest verificationRequest = objectMapper
                    .readValue(requestBody, OtpVerificationRequest.class);

            String sessionId = request.getHeader("X-Session-ID");
            log.debug("Received OTP verification request with sessionId: {}", sessionId);
            
            if (sessionId == null || sessionId.trim().isEmpty()) {
                log.warn("Missing or empty X-Session-ID header in OTP verification request");
                throw new BadCredentialsException("Session ID is required in X-Session-ID header");
            }

            OtpAuthenticationToken authRequest = new OtpAuthenticationToken(
                    null,
                    verificationRequest.otp(),
                    sessionId);
            
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            log.error("Error processing OTP verification request", e);
            throw new BadCredentialsException("Invalid request format");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
        return;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        failureHandler.onAuthenticationFailure(request, response, failed);
        return;
    }
}
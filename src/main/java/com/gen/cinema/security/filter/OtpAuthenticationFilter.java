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

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OtpAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private static final String OTP_EMAIL_SESSION_KEY = "OTP_EMAIL";

    public OtpAuthenticationFilter(
            String defaultFilterProcessesUrl,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // Check if this request has already been processed
        if (request.getAttribute("OTP_AUTH_PROCESSED") != null) {
            return null;
        }
        
        try {
            request.setAttribute("OTP_AUTH_PROCESSED", true);

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(OTP_EMAIL_SESSION_KEY) == null) {
                throw new BadCredentialsException("No OTP session found. Please request OTP first.");
            }

            String email = (String) session.getAttribute(OTP_EMAIL_SESSION_KEY);
            log.debug("Retrieved email from session: {}", email);

            BufferedReader reader = request.getReader();
            String requestBody = reader.lines().collect(Collectors.joining());

            if (requestBody == null || requestBody.trim().isEmpty()) {
                throw new BadCredentialsException("Request body is empty");
            }

            OtpVerificationRequest verificationRequest = objectMapper
                    .readValue(requestBody, OtpVerificationRequest.class);

            if (verificationRequest.otp() == null || verificationRequest.otp().isEmpty()) {
                throw new BadCredentialsException("OTP is required");
            }

            OtpAuthenticationToken authRequest = new OtpAuthenticationToken(
                    email,
                    verificationRequest.otp());
            
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            log.error("Error processing OTP verification request", e);
            throw new BadCredentialsException("Invalid request format");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(OTP_EMAIL_SESSION_KEY);
        }
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
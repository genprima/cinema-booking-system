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
import com.gen.cinema.dto.request.EmailLoginRequest;
import com.gen.cinema.security.authentication.EmailAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    public EmailAuthenticationFilter(
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
        try {

            BufferedReader reader = request.getReader();
            String requestBody = reader.lines().collect(Collectors.joining());
            
            if (requestBody == null || requestBody.trim().isEmpty()) {
                log.error("Empty request body received");
                throw new BadCredentialsException("Request body is empty");
            }

            EmailLoginRequest loginRequest = objectMapper.readValue(requestBody, EmailLoginRequest.class);

            if (loginRequest.email() == null || loginRequest.email().trim().isEmpty()) {
                throw new BadCredentialsException("Email is required");
            }

            EmailAuthenticationToken authRequest = new EmailAuthenticationToken(loginRequest.email());
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            log.error("Error processing authentication request", e);
            throw new BadCredentialsException("Invalid request format");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
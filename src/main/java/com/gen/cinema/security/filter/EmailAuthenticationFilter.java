package com.gen.cinema.security.filter;

import java.io.BufferedReader;
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

    public EmailAuthenticationFilter(
            String defaultFilterProcessesUrl,
            AuthenticationManager authenticationManager,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
        this.objectMapper = objectMapper;
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(successHandler);
        this.setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // Read the request body using BufferedReader
            BufferedReader reader = request.getReader();
            String requestBody = reader.lines().collect(Collectors.joining());
            
            log.debug("Received login request body: [{}]", requestBody);

            if (requestBody == null || requestBody.trim().isEmpty()) {
                log.error("Empty request body received");
                throw new BadCredentialsException("Request body is empty");
            }

            EmailLoginRequest loginRequest = objectMapper.readValue(requestBody, EmailLoginRequest.class);
            log.debug("Parsed login request: {}", loginRequest);

            if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
                throw new BadCredentialsException("Email is required");
            }

            EmailAuthenticationToken authRequest = new EmailAuthenticationToken(loginRequest.getEmail().trim());
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            log.error("Error processing authentication request", e);
            throw new BadCredentialsException("Invalid request format");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // Clear the cached body after successful authentication
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        // Clear the cached body after failed authentication
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
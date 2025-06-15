package com.gen.cinema.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.cinema.security.authentication.EmailAuthenticationToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private static final String OTP_EMAIL_SESSION_KEY = "OTP_EMAIL";

    public EmailAuthenticationSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        EmailAuthenticationToken authToken = (EmailAuthenticationToken) authentication;
        String email = authToken.getEmail();
        
        // Store email in session
        HttpSession session = request.getSession();
        session.setAttribute(OTP_EMAIL_SESSION_KEY, email);
        log.debug("Stored email in session: {}", email);

        Map<String, String> resultmap = new HashMap<>();
        resultmap.put("status", "success");
        resultmap.put("message", "OTP has been sent to your email");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), resultmap);
    }
} 
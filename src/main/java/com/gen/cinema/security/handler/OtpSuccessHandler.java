package com.gen.cinema.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.gen.cinema.domain.User;
import com.gen.cinema.security.authentication.OtpAuthenticationToken;
import com.gen.cinema.security.util.JwtTokenFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OtpSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtTokenFactory tokenFactory;

    public OtpSuccessHandler(ObjectMapper objectMapper, JwtTokenFactory tokenFactory) {
        this.objectMapper = objectMapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        log.info("OtpSuccessHandler called with authentication: {}", authentication);
        OtpAuthenticationToken authToken = (OtpAuthenticationToken) authentication;
        User user = (User) authToken.getPrincipal();
        log.info("User from authentication: {}", user);
        
        try {
            String token = tokenFactory.createAccessJwtToken(user, authToken.getAuthorities()).getToken();
            log.info("Generated JWT token: {}", token);

            Map<String, String> resultmap = new HashMap<>();
            resultmap.put("status", "success");
            resultmap.put("message", "Authentication successful");
            resultmap.put("token", token);
            resultmap.put("tokenType", "Bearer");
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Location", null);
            
            objectMapper.writeValue(response.getWriter(), resultmap);
            log.info("Response sent successfully");
        } catch (Exception e) {
            log.error("Error in OtpSuccessHandler: {}", e.getMessage(), e);
            throw e;
        }
    }

}

package com.gen.cinema.security.util;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gen.cinema.security.authentication.RawAccessJwtToken;
import com.gen.cinema.security.authentication.Token;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {
    public static String HEADER_PREFIX = "Bearer ";

    @Override
    public Token extract(String header) {
        if (!StringUtils.hasText(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        if (!header.startsWith(HEADER_PREFIX)) {
            throw new AuthenticationServiceException("Invalid authorization header format.");
        }

        return new RawAccessJwtToken(header.substring(HEADER_PREFIX.length()));
    }
} 
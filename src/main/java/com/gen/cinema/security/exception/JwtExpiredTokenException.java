package com.gen.cinema.security.exception;

import org.springframework.security.core.AuthenticationException;

import com.gen.cinema.security.authentication.RawAccessJwtToken;

public class JwtExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;
    
    private RawAccessJwtToken token;

    public JwtExpiredTokenException(RawAccessJwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
} 
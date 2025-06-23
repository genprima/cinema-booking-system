package com.gen.cinema.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class EmailAuthenticationToken extends AbstractAuthenticationToken {
    private final String email;
    private final String otp;
    private Object principal;

    public EmailAuthenticationToken(String email) {
        super(null);
        this.email = email;
        this.otp = null;
        this.setAuthenticated(false);
    }

    public EmailAuthenticationToken(Object principal, String email, String otp) {
        super(null);
        this.principal = principal;
        this.email = email;
        this.otp = otp;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }
} 
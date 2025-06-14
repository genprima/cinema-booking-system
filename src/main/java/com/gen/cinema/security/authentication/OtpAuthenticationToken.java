package com.gen.cinema.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class OtpAuthenticationToken extends AbstractAuthenticationToken {
    private final String email;
    private final String otp;
    private Object principal;

    public OtpAuthenticationToken(String email, String otp) {
        super(null);
        this.email = email;
        this.otp = otp;
        this.setAuthenticated(false);
    }

    public OtpAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        this.email = null;
        this.otp = null;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return otp;
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
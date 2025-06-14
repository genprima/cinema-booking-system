package com.gen.cinema.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import com.gen.cinema.domain.User;

public class OtpAuthenticationToken extends AbstractAuthenticationToken {

    private final String email;
    private final String otp;
    private User principal;

    public OtpAuthenticationToken(String email, String otp) {
        super(null);
        this.email = email;
        this.otp = otp;
        setAuthenticated(false);
    }

    public OtpAuthenticationToken(User principal) {
        super(null);
        this.email = principal.getEmail();
        this.otp = null;
        this.principal = principal;
        setAuthenticated(true);
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
} 
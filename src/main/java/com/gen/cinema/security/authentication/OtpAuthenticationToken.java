package com.gen.cinema.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class OtpAuthenticationToken extends AbstractAuthenticationToken {

    private final String email;
    private final String otp;
    private final String sessionId;
    private Object principal;

    public OtpAuthenticationToken(String email, String otp) {
        super(null);
        this.email = email;
        this.otp = otp;
        this.sessionId = null;
        setAuthenticated(false);
    }

    public OtpAuthenticationToken(String email, String otp, String sessionId) {
        super(null);
        this.email = email;
        this.otp = otp;
        this.sessionId = sessionId;
        setAuthenticated(false);
    }

    public OtpAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = null;
        this.otp = null;
        this.sessionId = null;
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

    public String getSessionId() {
        return sessionId;
    }
} 
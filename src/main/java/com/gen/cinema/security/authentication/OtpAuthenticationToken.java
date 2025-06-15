package com.gen.cinema.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class OtpAuthenticationToken extends AbstractAuthenticationToken {

    private final String email;
    private final String otp;
    private Object principal;

    public OtpAuthenticationToken(String email, String otp) {
        super(null);
        this.email = email;
        this.otp = otp;
        setAuthenticated(false);
    }

    public OtpAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = null;
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
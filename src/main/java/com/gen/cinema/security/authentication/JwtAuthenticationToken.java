package com.gen.cinema.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.gen.cinema.audit.Principal;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    
    private final RawAccessJwtToken rawAccessToken;
    private Principal principal;

    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(Principal principal, RawAccessJwtToken token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.rawAccessToken = token;
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public RawAccessJwtToken getToken() {
        return rawAccessToken;
    }
} 
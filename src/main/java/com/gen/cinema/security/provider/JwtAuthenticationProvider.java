package com.gen.cinema.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.gen.cinema.audit.Principal;
import com.gen.cinema.security.authentication.JwtAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private final Key key;

    public JwtAuthenticationProvider(Key key) {
        this.key = key;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authToken = (JwtAuthenticationToken) authentication;
        String token = authToken.getToken().getToken();

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String subject = claims.getSubject();
            @SuppressWarnings("unchecked")
            List<String> scopes = claims.get("scopes", List.class);
            List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Principal principal = new Principal(subject, authorities);
            
            return new JwtAuthenticationToken(principal, authToken.getToken(), principal.getAuthorities());

        } catch (Exception e) {
            log.debug("JWT token validation failed: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
} 
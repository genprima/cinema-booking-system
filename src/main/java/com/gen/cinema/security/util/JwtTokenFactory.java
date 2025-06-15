package com.gen.cinema.security.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.gen.cinema.domain.User;
import com.gen.cinema.security.authentication.RawAccessJwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenFactory {
    
    @Value("${jwt.expiration}")
    private Long expiration;

    private final Key key;

    public JwtTokenFactory(Key key) {
        this.key = key;
        log.debug("JwtTokenFactory initialized with key: {}", key);
    }

    public RawAccessJwtToken createAccessJwtToken(User user, Collection<? extends GrantedAuthority> collection) {
        log.debug("Creating JWT token for user: {}", user.getEmail());
        
        if (user.getEmail() == null) {
            throw new IllegalArgumentException("Cannot create JWT Token without email");
        }

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("scopes", collection.stream().map(s -> s.getAuthority()).collect(Collectors.toList()));
        claims.put("email", user.getEmail());
        log.debug("JWT claims created: {}", claims);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(expiration);

        Date currentDate = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        Date expirationDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
        log.debug("Token expiration set to: {}", expirationDate);

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(currentDate)
                    .setExpiration(expirationDate)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();
            log.debug("JWT token generated successfully");
            return new RawAccessJwtToken(token);
        } catch (Exception e) {
            log.error("Error generating JWT token: {}", e.getMessage(), e);
            throw e;
        }
    }
} 
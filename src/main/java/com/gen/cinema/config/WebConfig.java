package com.gen.cinema.config;

import com.fasterxml.jackson.databind.module.SimpleModule;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.time.Instant;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class WebConfig {
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private EmailConfig emailConfig;

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        
        SimpleModule module = new SimpleModule();
        module.addSerializer(Instant.class, new TimeZoneAwareInstantSerializer());
        builder.modules(module);
        
        return builder;
    }

    @Bean
    public Properties getProperties(){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", emailConfig.getSmtp().isAuth());
        prop.put("mail.smtp.starttls.enable", emailConfig.getSmtp().getStarttls().isEnable());
        prop.put("mail.smtp.host", emailConfig.getSmtp().getHost());
        prop.put("mail.smtp.port", emailConfig.getSmtp().getPort());
        prop.put("mail.smtp.ssl.trust", emailConfig.getSmtp().getSsl().getTrust());
        return prop;
    }

    @Bean
    public Session mailSession1(){
        return Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    emailConfig.getUsername(),
                    emailConfig.getPassword()
                );
            }
        });
    }

    @Bean
    public Key key() {
        // For HS512, we need a key that's at least 512 bits (64 bytes)
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        if (keyBytes.length < 64) {
            // If the key is too short, pad it with zeros to reach 64 bytes
            byte[] paddedKey = new byte[64];
            System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 64));
            return Keys.hmacShaKeyFor(paddedKey);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
} 
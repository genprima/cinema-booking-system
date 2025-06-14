package com.gen.cinema.config;

import com.fasterxml.jackson.databind.module.SimpleModule;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import java.time.Instant;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class WebConfig {
    

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
        Session session = Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return passwordAuthentication1();
            }
        });
        return session;
    }

    @Bean
    public PasswordAuthentication passwordAuthentication1(){
        return new PasswordAuthentication(
            emailConfig.getUsername(),
            emailConfig.getPassword()
        );
    }
} 
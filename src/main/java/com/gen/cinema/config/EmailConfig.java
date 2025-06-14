package com.gen.cinema.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
public class EmailConfig {
    private String username;
    private String password;
    private Smtp smtp;

    @Getter
    @Setter
    public static class Smtp {
        private boolean auth;
        private Starttls starttls;
        private String host;
        private int port;
        private Ssl ssl;
    }

    @Getter
    @Setter
    public static class Starttls {
        private boolean enable;
    }

    @Getter
    @Setter
    public static class Ssl {
        private String trust;
    }
} 
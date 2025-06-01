package com.gen.cinema.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import java.time.Instant;

@Configuration
public class WebConfig {
    
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        
        SimpleModule module = new SimpleModule();
        module.addSerializer(Instant.class, new TimeZoneAwareInstantSerializer());
        builder.modules(module);
        
        return builder;
    }
} 
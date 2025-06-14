package com.gen.cinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.cinema.security.CustomAuthenticationEntryPoint;
import com.gen.cinema.security.CustomAccessDeniedHandler;
import com.gen.cinema.security.filter.EmailAuthenticationFilter;
import com.gen.cinema.security.filter.OtpAuthenticationFilter;
import com.gen.cinema.security.handler.EmailAuthenticationSuccessHandler;
import com.gen.cinema.security.handler.OtpSuccessHandler;
import com.gen.cinema.security.handler.AuthFailureHandler;
import com.gen.cinema.security.provider.EmailAuthenticationProvider;
import com.gen.cinema.security.provider.OtpAuthenticationProvider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ProviderManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    private static final String AUTH_URL = "/v1/auth/login";
    private static final String OTP_URL = "/v1/auth/verify-otp";
    private static final String V1_URL = "/v1/**";

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList("http://localhost:3000", "http://localhost:8080");
    private static final List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS");
    private static final List<String> ALLOWED_HEADERS = Arrays.asList("Authorization", "Content-Type", "X-Requested-With");

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final TimeZoneFilter timeZoneFilter;
    private final EmailAuthenticationProvider emailAuthenticationProvider;
    private final OtpAuthenticationProvider otpAuthenticationProvider;
    
    public SecurityConfig(
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler,
            TimeZoneFilter timeZoneFilter,
            EmailAuthenticationProvider emailAuthenticationProvider,
            OtpAuthenticationProvider otpAuthenticationProvider, ObjectMapper objectMapper) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.timeZoneFilter = timeZoneFilter;
        this.emailAuthenticationProvider = emailAuthenticationProvider;
        this.otpAuthenticationProvider = otpAuthenticationProvider;
        this.objectMapper = objectMapper;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationSuccessHandler otpSuccessHandler() {
        return new OtpSuccessHandler(objectMapper);
    }

    @Bean
    public AuthenticationSuccessHandler emailAuthenticationSuccessHandler() {
        return new EmailAuthenticationSuccessHandler(objectMapper);
    }

    @Bean
    public AuthenticationFailureHandler authFailureHandler() {
        return new AuthFailureHandler(objectMapper);
    }
    

    @Bean
    public EmailAuthenticationFilter emailAuthenticationFilter(
            AuthenticationManager authenticationManager,
            @Qualifier("emailAuthenticationSuccessHandler") AuthenticationSuccessHandler successHandler,
            @Qualifier("authFailureHandler") AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        return new EmailAuthenticationFilter(AUTH_URL, authenticationManager, successHandler, failureHandler, objectMapper);
    }

    @Bean
    public OtpAuthenticationFilter otpAuthenticationFilter(
            AuthenticationManager authenticationManager,
            @Qualifier("otpSuccessHandler") AuthenticationSuccessHandler successHandler,
            @Qualifier("authFailureHandler") AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        return new OtpAuthenticationFilter(OTP_URL, authenticationManager, successHandler, failureHandler, objectMapper);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        ((ProviderManager) authenticationManager).getProviders().add(emailAuthenticationProvider);
        ((ProviderManager) authenticationManager).getProviders().add(otpAuthenticationProvider);
        return authenticationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            EmailAuthenticationFilter emailAuthenticationFilter,
            OtpAuthenticationFilter otpAuthenticationFilter) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(timeZoneFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(emailAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(otpAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTH_URL, OTP_URL).permitAll()
                .requestMatchers(V1_URL).authenticated()
                .anyRequest().authenticated()
            );
        
        return http.build();
    }
} 
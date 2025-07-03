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
import com.gen.cinema.security.filter.JwtTokenAuthenticationProcessingFilter;
import com.gen.cinema.security.handler.EmailAuthenticationSuccessHandler;
import com.gen.cinema.security.handler.OtpSuccessHandler;
import com.gen.cinema.security.handler.AuthFailureHandler;
import com.gen.cinema.security.provider.EmailAuthenticationProvider;
import com.gen.cinema.security.provider.OtpAuthenticationProvider;
import com.gen.cinema.security.provider.JwtAuthenticationProvider;
import com.gen.cinema.security.util.JwtTokenFactory;
import com.gen.cinema.security.util.JwtHeaderTokenExtractor;
import com.gen.cinema.security.util.SkipPathRequestMatcher;
import com.gen.cinema.service.EmailService;
import com.gen.cinema.service.SessionService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ProviderManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtTokenFactory jwtTokenFactory;
    private final JwtHeaderTokenExtractor tokenExtractor;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private static final String AUTH_URL = "/v1/auth/login";
    private static final String OTP_URL = "/v1/auth/otp";
    private static final String TEST_SESSION_URL = "/v1/auth/test-session";
    private static final String V1_URL = "/v1/**";
    private static final String[] SWAGGER_URLS = {
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/v3/api-docs.yaml",
        "/swagger-resources/**",
        "/webjars/**"
    };

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final TimeZoneFilter timeZoneFilter;
    private final EmailAuthenticationProvider emailAuthenticationProvider;
    private final OtpAuthenticationProvider otpAuthenticationProvider;
    private final EmailService emailService;
    private final SessionService sessionService;

    public SecurityConfig(
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler,
            TimeZoneFilter timeZoneFilter,
            EmailAuthenticationProvider emailAuthenticationProvider,
            OtpAuthenticationProvider otpAuthenticationProvider,
            ObjectMapper objectMapper,
            JwtTokenFactory jwtTokenFactory,
            JwtHeaderTokenExtractor tokenExtractor,
            JwtAuthenticationProvider jwtAuthenticationProvider,
            EmailService emailService,
            SessionService sessionService) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.timeZoneFilter = timeZoneFilter;
        this.emailAuthenticationProvider = emailAuthenticationProvider;
        this.otpAuthenticationProvider = otpAuthenticationProvider;
        this.objectMapper = objectMapper;
        this.jwtTokenFactory = jwtTokenFactory;
        this.tokenExtractor = tokenExtractor;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.emailService = emailService;
        this.sessionService = sessionService;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationSuccessHandler otpSuccessHandler() {
        return new OtpSuccessHandler(objectMapper, jwtTokenFactory);
    }

    @Bean
    public AuthenticationSuccessHandler emailAuthenticationSuccessHandler() {
        return new EmailAuthenticationSuccessHandler(objectMapper, emailService, sessionService);
    }

    @Bean
    public AuthenticationFailureHandler authFailureHandler() {
        return new AuthFailureHandler(objectMapper);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        ((ProviderManager) authenticationManager).getProviders().add(emailAuthenticationProvider);
        ((ProviderManager) authenticationManager).getProviders().add(otpAuthenticationProvider);
        ((ProviderManager) authenticationManager).getProviders().add(jwtAuthenticationProvider);
        return authenticationManager;
    }
    

    @Bean
    public EmailAuthenticationFilter emailAuthenticationFilter(
            AuthenticationManager authenticationManager,
            @Qualifier("emailAuthenticationSuccessHandler") AuthenticationSuccessHandler successHandler,
            @Qualifier("authFailureHandler") AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        EmailAuthenticationFilter emailAuthenticationFilter = new EmailAuthenticationFilter(AUTH_URL, successHandler, failureHandler,
                objectMapper);
        emailAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return emailAuthenticationFilter;
    }

    @Bean
    public OtpAuthenticationFilter otpAuthenticationFilter(
            AuthenticationManager authenticationManager,
            @Qualifier("otpSuccessHandler") AuthenticationSuccessHandler successHandler,
            @Qualifier("authFailureHandler") AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        OtpAuthenticationFilter otpAuthenticationFilter = new OtpAuthenticationFilter(OTP_URL, successHandler, failureHandler,
                objectMapper, sessionService);
        otpAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return otpAuthenticationFilter;
    }

    @Bean
    public JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter(
            AuthenticationManager authenticationManager,
            AuthenticationFailureHandler failureHandler) {
        List<String> pathsToSkip = Arrays.asList(AUTH_URL, OTP_URL, TEST_SESSION_URL);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, V1_URL);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(
                failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            EmailAuthenticationFilter emailAuthenticationFilter,
            OtpAuthenticationFilter otpAuthenticationFilter,
            JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(timeZoneFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(otpAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(emailAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_URL, OTP_URL, TEST_SESSION_URL).permitAll()
                        .requestMatchers(SWAGGER_URLS).permitAll()
                        .requestMatchers(V1_URL).authenticated()
                        .anyRequest().authenticated())
                .securityMatcher(V1_URL)
                .anonymous(anonymous -> anonymous
                        .disable());

        return http.build();
    }
}
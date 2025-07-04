package com.gen.cinema.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gen.cinema.dto.request.EmailLoginRequest;
import com.gen.cinema.dto.request.OtpVerificationRequest;
import com.gen.cinema.dto.response.AuthResponse;
import com.gen.cinema.dto.response.TokenResponse;
import com.gen.cinema.dto.response.ErrorResponse;
import com.gen.cinema.security.authentication.EmailAuthenticationToken;
import com.gen.cinema.security.authentication.OtpAuthenticationToken;
import com.gen.cinema.security.handler.EmailAuthenticationSuccessHandler;
import com.gen.cinema.security.handler.OtpSuccessHandler;
import com.gen.cinema.security.handler.AuthFailureHandler;
import com.gen.cinema.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@Validated
@Tag(name = "Authentication", description = "Authentication endpoints for email login and OTP verification")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final EmailAuthenticationSuccessHandler emailSuccessHandler;
    private final OtpSuccessHandler otpSuccessHandler;
    private final AuthFailureHandler failureHandler;
    private final SessionService sessionService;

    public AuthController(
            AuthenticationManager authenticationManager,
            EmailAuthenticationSuccessHandler emailSuccessHandler,
            OtpSuccessHandler otpSuccessHandler,
            AuthFailureHandler failureHandler,
            SessionService sessionService,
            ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.emailSuccessHandler = emailSuccessHandler;
        this.otpSuccessHandler = otpSuccessHandler;
        this.failureHandler = failureHandler;
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    @Operation(
        summary = "Email Login",
        description = "Send login request with email to receive OTP. The system will generate and send an OTP to the provided email address.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EmailLoginRequest.class),
                examples = @ExampleObject(
                    name = "Email Login Example",
                    value = "{\"email\": \"user@example.com\"}"
                )
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "OTP sent successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponse.class),
                examples = @ExampleObject(
                    name = "Success Response",
                    value = "{\"status\": \"success\", \"message\": \"OTP has been sent to your email\", \"sessionId\": \"uuid-session-id\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Authentication failed",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Error Response",
                    value = "{\"error\": \"Authentication Failed\", \"message\": \"User not found\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - invalid email format",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Validation Error",
                    value = "{\"error\": \"Bad Request\", \"message\": \"Invalid email format\"}"
                )
            )
        )
    })
    public ResponseEntity<?> login(
            @RequestBody @Valid EmailLoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) throws IOException, ServletException {
        
        try {
            EmailAuthenticationToken authRequest = new EmailAuthenticationToken(request.email());
            Authentication authentication = authenticationManager.authenticate(authRequest);
            
            if (authentication.isAuthenticated()) {
                emailSuccessHandler.onAuthenticationSuccess(httpRequest, httpResponse, authentication);
                return ResponseEntity.ok().build(); // Response already written by handler
            } else {
                return ResponseEntity.status(401).body(new ErrorResponse("Authentication failed", "Invalid credentials"));
            }
            
        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(httpRequest, httpResponse, e);
            return ResponseEntity.status(401).body(new ErrorResponse("Authentication Failed", e.getMessage()));
        }
    }

    @PostMapping("/otp")
    @Operation(
        summary = "OTP Verification",
        description = "Verify OTP to complete authentication and receive JWT token. Session ID must be provided in X-Session-ID header.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OtpVerificationRequest.class),
                examples = @ExampleObject(
                    name = "OTP Verification Example",
                    value = "{\"otp\": \"123456\"}"
                )
            )
        )
    )
    @io.swagger.v3.oas.annotations.Parameter(
        name = "X-Session-ID",
        description = "Session ID received from login endpoint (required)",
        in = io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER,
        required = true,
        schema = @Schema(type = "string", example = "uuid-session-id")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Authentication successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TokenResponse.class),
                examples = @ExampleObject(
                    name = "Success Response",
                    value = "{\"status\": \"success\", \"message\": \"Authentication successful\", \"token\": \"jwt-token-here\", \"tokenType\": \"Bearer\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Authentication failed",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Error Response",
                    value = "{\"error\": \"Authentication Failed\", \"message\": \"Invalid OTP\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - invalid input",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(
                    name = "Validation Error",
                    value = "{\"error\": \"Bad Request\", \"message\": \"Invalid request format\"}"
                )
            )
        )
    })
    public ResponseEntity<?> verifyOtp(
            @RequestBody @Valid OtpVerificationRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) throws IOException, ServletException {
        
        try {
            String sessionId = httpRequest.getHeader("X-Session-ID");
            if (sessionId == null || sessionId.isEmpty()) {
                return ResponseEntity.status(400).body(new ErrorResponse("Bad Request", "X-Session-ID header is required"));
            }
            
            OtpAuthenticationToken authRequest = new OtpAuthenticationToken(
                null,
                request.otp(), 
                sessionId
            );
            
            Authentication authentication = authenticationManager.authenticate(authRequest);
            
            if (authentication.isAuthenticated()) {
                otpSuccessHandler.onAuthenticationSuccess(httpRequest, httpResponse, authentication);
                return ResponseEntity.ok().build(); // Response already written by handler
            } else {
                return ResponseEntity.status(401).body(new ErrorResponse("Authentication failed", "Invalid credentials"));
            }
            
        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(httpRequest, httpResponse, e);
            return ResponseEntity.status(401).body(new ErrorResponse("Authentication Failed", e.getMessage()));
        }
    }

    @PostMapping("/test-session")
    @Operation(
        summary = "Test Session Service",
        description = "Test endpoint to verify session service functionality (for debugging)",
        hidden = true
    )
    @io.swagger.v3.oas.annotations.Parameter(
        name = "X-Session-ID",
        description = "Session ID to test",
        in = io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER,
        required = true,
        schema = @Schema(type = "string", example = "uuid-session-id")
    )
    public ResponseEntity<?> testSession(HttpServletRequest httpRequest) {
        String sessionId = httpRequest.getHeader("X-Session-ID");
        
        if (sessionId == null || sessionId.isEmpty()) {
            return ResponseEntity.status(400).body(new ErrorResponse("Bad Request", "X-Session-ID header is required"));
        }
        
        var sessionData = sessionService.getSessionData(sessionId);
        if (sessionData.isPresent()) {
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Session is valid",
                "sessionId", sessionId,
                "sessionData", sessionData.get()
            ));
        } else {
            return ResponseEntity.status(404).body(new ErrorResponse("Not Found", "Session not found or expired"));
        }
    }
} 
package com.gen.cinema.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT token response")
public record TokenResponse(
    @Schema(description = "Status of the operation", example = "success")
    String status,
    
    @Schema(description = "Response message", example = "Authentication successful")
    String message,
    
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String token,
    
    @Schema(description = "Token type", example = "Bearer")
    String tokenType
) {} 
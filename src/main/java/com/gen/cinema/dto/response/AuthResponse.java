package com.gen.cinema.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response")
public record AuthResponse(
    @Schema(description = "Status of the operation", example = "success")
    String status,
    
    @Schema(description = "Response message", example = "OTP has been sent to your email")
    String message,
    
    @Schema(description = "Session ID for OTP verification", example = "uuid-session-id")
    String sessionId
) {} 
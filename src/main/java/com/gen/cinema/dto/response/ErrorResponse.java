package com.gen.cinema.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response")
public record ErrorResponse(
    @Schema(description = "Error type", example = "Authentication Failed")
    String error,
    
    @Schema(description = "Error message", example = "User not found")
    String message
) {} 
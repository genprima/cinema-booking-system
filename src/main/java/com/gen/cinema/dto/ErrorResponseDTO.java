package com.gen.cinema.dto;



public record ErrorResponseDTO(String code, String message) {
    public static ErrorResponseDTO defaultError() {
        return new ErrorResponseDTO(
            "SYSTEM_ERROR",
            "There something with the system, please contact developer"
        );
    }
} 
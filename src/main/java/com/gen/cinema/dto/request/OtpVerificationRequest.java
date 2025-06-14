package com.gen.cinema.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class OtpVerificationRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "OTP is required")
    private String otp;

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }
} 
package com.gen.cinema.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class OtpVerificationRequest implements Serializable {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "OTP is required")
    private String otp;

    public OtpVerificationRequest() {
        // Default constructor for Jackson
    }

    public OtpVerificationRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
} 
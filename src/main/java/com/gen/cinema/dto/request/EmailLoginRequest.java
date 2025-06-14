package com.gen.cinema.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailLoginRequest implements Serializable {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public EmailLoginRequest() {
        // Default constructor for Jackson
    }

    public EmailLoginRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
} 
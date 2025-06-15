package com.gen.cinema.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailLoginRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email
) implements Serializable {} 
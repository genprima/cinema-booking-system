package com.gen.cinema.dto.request;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookingRequest(
    @NotNull(message = "Schedule seat IDs cannot be null")
    @NotEmpty(message = "At least one seat must be selected")
    List<String> scheduleSeatIds
) {} 
package com.gen.cinema.dto.request;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.gen.cinema.validation.annotation.SameSchedule;
import com.gen.cinema.validation.annotation.NotAvailableSeats;
import com.gen.cinema.validation.annotation.LeaveOneAvailable;

public record BookingRequest(
    @NotNull(message = "Schedule seat IDs cannot be null")
    @NotEmpty(message = "At least one seat must be selected")
    @SameSchedule
    @NotAvailableSeats
    @LeaveOneAvailable
    List<String> scheduleSeatIds
) {} 
package com.gen.cinema.validation.validator;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.gen.cinema.enums.SeatStatus;
import com.gen.cinema.repository.MovieScheduleSeatRepository;
import com.gen.cinema.validation.annotation.NotAvailableSeats;

public class NotAvailableSeatsValidator implements ConstraintValidator<NotAvailableSeats, List<String>> {
    
    @Autowired
    private MovieScheduleSeatRepository movieScheduleSeatRepository;
    
    @Override
    public boolean isValid(List<String> scheduleSeatIds, ConstraintValidatorContext context) {
        if (scheduleSeatIds == null || scheduleSeatIds.isEmpty()) {
            return false;
        }

        // Convert string IDs to UUIDs
        List<UUID> secureIds = scheduleSeatIds.stream()
            .map(UUID::fromString)
            .toList();

        // Check if any of the requested seats are not available
        return movieScheduleSeatRepository.countBySecureIdInAndStatusNot(secureIds, SeatStatus.AVAILABLE) == 0;
    }
} 
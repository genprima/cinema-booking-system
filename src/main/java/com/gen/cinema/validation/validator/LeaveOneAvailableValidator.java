package com.gen.cinema.validation.validator;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.gen.cinema.repository.MovieScheduleSeatRepository;
import com.gen.cinema.validation.annotation.LeaveOneAvailable;

public class LeaveOneAvailableValidator implements ConstraintValidator<LeaveOneAvailable, List<String>> {
    
    @Autowired
    private MovieScheduleSeatRepository movieScheduleSeatRepository;
    
    @Override
    public boolean isValid(List<String> scheduleSeatIds, ConstraintValidatorContext context) {
        if (scheduleSeatIds == null || scheduleSeatIds.isEmpty()) {
            return true;
        }

        List<UUID> secureIds = scheduleSeatIds.stream()
            .map(UUID::fromString)
            .toList();

        // Check if there's a pattern where x-1 is AVAILABLE and x-2 is NOT AVAILABLE
        long count = movieScheduleSeatRepository.countBySecureIdsWithPattern(secureIds);

        return count == 0;
    }
} 
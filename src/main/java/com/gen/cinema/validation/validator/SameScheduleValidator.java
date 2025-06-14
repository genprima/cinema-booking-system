package com.gen.cinema.validation.validator;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.gen.cinema.repository.MovieScheduleSeatRepository;
import com.gen.cinema.validation.annotation.SameSchedule;

public class SameScheduleValidator implements ConstraintValidator<SameSchedule, List<String>> {
    
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

        // Check if all seats are from the same schedule
        return movieScheduleSeatRepository.countDistinctMovieSchedules(secureIds) == 1;
    }
} 
package com.gen.cinema.validation.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gen.cinema.audit.Principal;
import com.gen.cinema.domain.Booking;
import com.gen.cinema.enums.UserRole;
import com.gen.cinema.repository.BookingRepository;
import com.gen.cinema.validation.annotation.CanAccessBooking;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class CanAccessBookingValidator implements ConstraintValidator<CanAccessBooking, String> {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public boolean isValid(String bookingId, ConstraintValidatorContext context) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            return false;
        }

        Principal principal = (Principal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userEmail = principal.getEmail();

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ADMIN.name()))) {
            return true;
        }

        Booking booking = bookingRepository.findBySecureIdAndUserEmail(UUID.fromString(bookingId), userEmail)
                .orElse(null);
        if (booking == null) {
            return false;
        }

        return booking.getUser().getEmail().equals(userEmail);
    }
}
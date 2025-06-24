package com.gen.cinema.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.gen.cinema.validation.validator.CanAccessBookingValidator;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CanAccessBookingValidator.class)
public @interface CanAccessBooking {
    String message() default "You are not authorized to access this booking";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 
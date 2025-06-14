package com.gen.cinema.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.gen.cinema.validation.validator.NotAvailableSeatsValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotAvailableSeatsValidator.class)
public @interface NotAvailableSeats {
    String message() default "One or more selected seats are not available";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 
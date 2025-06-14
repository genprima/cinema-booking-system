package com.gen.cinema.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.gen.cinema.validation.validator.LeaveOneAvailableValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LeaveOneAvailableValidator.class)
public @interface LeaveOneAvailable {
    String message() default "Cannot leave a single available seat between booked seats";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 
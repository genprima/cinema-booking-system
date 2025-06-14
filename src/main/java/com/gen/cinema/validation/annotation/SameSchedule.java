package com.gen.cinema.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.gen.cinema.validation.validator.SameScheduleValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SameScheduleValidator.class)
public @interface SameSchedule {
    String message() default "All seats must be from the same schedule";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 
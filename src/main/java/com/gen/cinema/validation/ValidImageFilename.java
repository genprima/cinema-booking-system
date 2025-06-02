package com.gen.cinema.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidImageFilenameValidator.class)
public @interface ValidImageFilename {
    String message() default "Invalid filename. Must be provided and have a valid image extension (jpg, jpeg, png, gif)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 
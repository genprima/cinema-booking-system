package com.gen.cinema.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueMovieTitleValidator.class)
public @interface UniqueMovieTitle {
    String message() default "Movie title already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 
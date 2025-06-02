package com.gen.cinema.validation;

import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

@Component
public class ValidImageFilenameValidator implements ConstraintValidator<ValidImageFilename, String> {

    private static final Set<String> VALID_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif");

    @Override
    public boolean isValid(String filename, ConstraintValidatorContext context) {
        if (filename == null || filename.isBlank()) {
            return false;
        }

        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return false;
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        return VALID_EXTENSIONS.contains(extension);
    }
} 
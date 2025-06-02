package com.gen.cinema.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gen.cinema.repository.MovieRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueMovieTitleValidator implements ConstraintValidator<UniqueMovieTitle, String> {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public boolean isValid(String newTitle, ConstraintValidatorContext context) {
        if (newTitle == null) {
            return true;
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String path = attributes.getRequest().getRequestURI();
            if (path.matches("/v1/movies/\\d+")) {
                Long movieId = Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
                return movieRepository.findById(movieId)
                    .map(movie -> movie.getTitle().equalsIgnoreCase(newTitle) || !movieRepository.existsByTitle(newTitle))
                    .orElse(!movieRepository.existsByTitle(newTitle));
            }
        }

        return !movieRepository.existsByTitle(newTitle);
    }
} 
package com.gen.cinema.dto.request;

import com.gen.cinema.validation.UniqueMovieTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMovieRequestDTO(
    
    @NotBlank(message = "Title is required")
    @UniqueMovieTitle(message = "Movie title already exists")
    String title,
    
    @NotBlank(message = "Description is required")
    String description,
    
    @NotBlank(message = "Synopsis is required")
    String synopsis,
    
    @NotBlank(message = "Rating is required")
    String rating,
    
    @NotNull(message = "Duration is required")
    Integer duration,
    
    @NotBlank(message = "Director is required")
    String director
) {} 
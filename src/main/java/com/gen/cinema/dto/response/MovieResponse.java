package com.gen.cinema.dto.response;

public record MovieResponse(
    Long id,
    String title,
    String description,
    Integer duration,
    Long version
) {
   
} 
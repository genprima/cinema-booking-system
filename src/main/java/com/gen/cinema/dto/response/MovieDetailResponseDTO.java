package com.gen.cinema.dto.response;

public record MovieDetailResponseDTO(
    Long id,
    String title,
    String description,
    String synopsis,
    String rating,
    Integer duration,
    String imageUrl
) {} 
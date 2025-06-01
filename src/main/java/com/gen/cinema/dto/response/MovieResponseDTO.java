package com.gen.cinema.dto.response;

public record MovieResponseDTO(
    Long id,
    String title,
    String description,
    Integer duration,
    String imageUrl
) {} 
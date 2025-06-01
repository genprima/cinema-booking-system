package com.gen.cinema.dto.response;

public record StudioResponseDTO(
    Long id,
    String name,
    String studioLayoutName,
    Integer maxRows,
    Integer maxColumns
) {} 
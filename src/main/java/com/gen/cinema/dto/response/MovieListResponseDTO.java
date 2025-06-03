package com.gen.cinema.dto.response;

import java.time.Instant;

public record MovieListResponseDTO(
    Long id,
    String title,
    String description,
    Integer duration,
    String createdBy,
    String modifiedBy,
    Instant createdDate,
    Instant modifiedDate
) {} 
package com.gen.cinema.dto.response;

import java.util.List;

public record MovieScheduleDayResponse(
    String date,      // e.g. "2024-05-20"
    String dayName,   // e.g. "Monday"
    List<ShowTimeResponse> showtimes,
    String studioName,
    MovieDetail movie
) {
    public record MovieDetail(
        Long id,
        String title,
        String description,
        Integer duration,
        String synopsis
    ) {}
} 
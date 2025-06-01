package com.gen.cinema.dto.response;

import java.util.List;

public record CinemaResponse(
    Long id,
    String name,
    String address,
    List<MovieResponse> movies,
    Long version
) {} 
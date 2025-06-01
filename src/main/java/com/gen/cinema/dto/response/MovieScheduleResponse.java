package com.gen.cinema.dto.response;

import java.util.List;
 
public record MovieScheduleResponse(
    MovieScheduleDayResponse.MovieDetail movie,
    List<MovieScheduleDayResponse> schedules
) {} 
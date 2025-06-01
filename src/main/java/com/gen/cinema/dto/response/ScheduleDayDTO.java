package com.gen.cinema.dto.response;

public record ScheduleDayDTO(
    String date,
    String label,
    boolean isToday
) {} 
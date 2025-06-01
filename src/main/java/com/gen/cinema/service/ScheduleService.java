package com.gen.cinema.service;

import com.gen.cinema.dto.response.ScheduleDaysResponseDTO;

public interface ScheduleService {
    ScheduleDaysResponseDTO getScheduleDays(Long studioId, Long movieId);
} 
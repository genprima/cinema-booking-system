package com.gen.cinema.service;

import com.gen.cinema.dto.response.BookingScheduleResponse;
import com.gen.cinema.dto.response.ScheduleDaysResponseDTO;
import com.gen.cinema.dto.response.ScheduleTimesResponseDTO;

public interface ScheduleService {
    ScheduleDaysResponseDTO getScheduleDays(Long studioId, Long movieId);
    ScheduleTimesResponseDTO getScheduleTimes(Long studioId, Long movieId, Long dateEpochMillis);
    BookingScheduleResponse getScheduleDetail(String scheduleId);
} 
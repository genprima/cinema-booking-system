package com.gen.cinema.service.impl;

import com.gen.cinema.dto.response.ScheduleDayDTO;
import com.gen.cinema.dto.response.ScheduleDaysResponseDTO;
import com.gen.cinema.repository.MovieScheduleRepository;
import com.gen.cinema.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final MovieScheduleRepository movieScheduleRepository;

    public ScheduleServiceImpl(MovieScheduleRepository movieScheduleRepository) {
        this.movieScheduleRepository = movieScheduleRepository;
    }

    @Override
    public ScheduleDaysResponseDTO getScheduleDays(Long studioId, Long movieId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        List<Date> dates = movieScheduleRepository.findDistinctScheduleDatesByStudioMovie(studioId, movieId, startOfToday);
        List<ScheduleDayDTO> dayDTOs = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM");
        
        for (Date dateSql : dates) {
            LocalDate date = dateSql.toLocalDate();
            String dateStr = date.format(dateFormatter);
            boolean isToday = date.equals(today);
            String label = isToday ? "Today" : date.getDayOfWeek().toString().substring(0, 1) + date.getDayOfWeek().toString().substring(1,3).toLowerCase();
            dayDTOs.add(new ScheduleDayDTO(dateStr, label, isToday));
        }
        return new ScheduleDaysResponseDTO(dayDTOs);
    }
} 
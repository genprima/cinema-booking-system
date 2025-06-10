package com.gen.cinema.service.impl;

import com.gen.cinema.dto.response.ScheduleDayDTO;
import com.gen.cinema.dto.response.ScheduleDaysResponseDTO;
import com.gen.cinema.dto.response.ScheduleTimeDTO;
import com.gen.cinema.dto.response.ScheduleTimesResponseDTO;
import com.gen.cinema.repository.MovieScheduleRepository;
import com.gen.cinema.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gen.cinema.domain.MovieSchedule;
import com.gen.cinema.dto.response.BookingScheduleResponse;
import com.gen.cinema.dto.response.BookingSeatResponse;
import com.gen.cinema.exception.BadRequestAlertException;
import com.gen.cinema.projection.ScheduleSeatProjection;

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

    @Override
    public ScheduleTimesResponseDTO getScheduleTimes(Long studioId, Long movieId, Long dateEpochMillis) {
        LocalDate date = Instant.ofEpochMilli(dateEpochMillis).atZone(ZoneId.systemDefault()).toLocalDate();
        var schedules = movieScheduleRepository.findSchedulesByStudioMovieAndDate(studioId, movieId, date);
        List<ScheduleTimeDTO> timeDTOs = new ArrayList<>();
        
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH : mm");
        for (var ms : schedules) {
            String timeStr = ms.getStartTime().format(timeFormatter);
            timeDTOs.add(new ScheduleTimeDTO(ms.getSecureId().toString(), timeStr));
        }
        return new ScheduleTimesResponseDTO(timeDTOs);
    }

    @Override
    public BookingScheduleResponse getScheduleDetail(String scheduleId) {
        
        MovieSchedule schedule = movieScheduleRepository.findBySecureId(UUID.fromString(scheduleId))
            .orElseThrow(() -> new BadRequestAlertException("Schedule not found with id: " + scheduleId));

        List<ScheduleSeatProjection> seatProjections = movieScheduleRepository.findScheduleSeatsByScheduleId(UUID.fromString(scheduleId));
        
        List<BookingSeatResponse> seats = seatProjections.stream()
            .map(seat -> new BookingSeatResponse(
                seat.getId().toString(),
                seat.getRow(),
                seat.getNumber(),
                seat.getX(),
                seat.getY(),
                seat.getStatus(),
                seat.getPrice(),
                seat.getSeatType()
            ))
            .collect(Collectors.toList());

        return new BookingScheduleResponse(
            schedule.getSecureId().toString(),
            schedule.getStartTime().toString(),
            seats
        );
    }
} 
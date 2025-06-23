package com.gen.cinema.web;

import com.gen.cinema.dto.response.ScheduleDaysResponseDTO;
import com.gen.cinema.dto.response.ScheduleTimesResponseDTO;
import com.gen.cinema.dto.response.BookingScheduleResponse;
import com.gen.cinema.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/schedule")
@Validated
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ScheduleDaysResponseDTO> getScheduleDays(
            @RequestParam @NotNull(message = "studioId is required") Long studioId,
            @RequestParam @NotNull(message = "movieId is required") Long movieId) {
        return ResponseEntity.ok(scheduleService.getScheduleDays(studioId, movieId));
    }

    @GetMapping("/times")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ScheduleTimesResponseDTO> getScheduleTimes(
            @RequestParam @NotNull(message = "studioId is required") Long studioId,
            @RequestParam @NotNull(message = "movieId is required") Long movieId,
            @RequestParam @NotNull(message = "date is required (epoch millis)") Long date) {
        return ResponseEntity.ok(scheduleService.getScheduleTimes(studioId, movieId, date));
    }

    @GetMapping("/{scheduleId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingScheduleResponse> getScheduleDetail(
            @PathVariable @NotNull(message = "scheduleId is required") String scheduleId) {
        return ResponseEntity.ok(scheduleService.getScheduleDetail(scheduleId));
    }
} 
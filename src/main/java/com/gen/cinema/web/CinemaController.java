package com.gen.cinema.web;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gen.cinema.dto.response.CinemaResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.service.CinemaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1/cinema")
@Validated
@SecurityRequirement(name = "Bearer Authentication")
public class CinemaController {

    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping
    public ResponseEntity<ResultPageResponseDTO<CinemaResponseDTO>> getCinemasByCity(
            @RequestParam @NotBlank(message = "City code is required") String cityCode,
            @RequestParam(defaultValue = "") String cinemaName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cinema.name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        return ResponseEntity.ok(cinemaService.getCinemasByCityCode(cityCode, cinemaName, page, size, sortBy, direction));
    }
} 
package com.gen.cinema.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.dto.response.StudioResponseDTO;
import com.gen.cinema.service.StudioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/studio")
@Validated
@SecurityRequirement(name = "Bearer Authentication")
public class StudioController {

    private final StudioService studioService;

    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    @GetMapping("/cinema/{cinemaCode}/movie/{movieId}")
    @Valid
    public ResponseEntity<ResultPageResponseDTO<StudioResponseDTO>> getStudiosByCinemaAndMovie(
            @PathVariable @NotBlank(message = "Cinema code is required") String cinemaCode,
            @PathVariable @NotNull(message = "Movie ID is required") @Positive(message = "Movie ID must be positive") Long movieId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(studioService.getStudiosByCinemaCodeAndMovieId(cinemaCode, movieId, pageable));
    }
} 
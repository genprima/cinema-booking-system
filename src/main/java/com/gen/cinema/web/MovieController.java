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

import com.gen.cinema.dto.response.MovieResponseDTO;
import com.gen.cinema.dto.response.MovieDetailResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.service.MovieService;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/movies")
@Validated
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/cinema/{cinemaCode}")
    public ResponseEntity<ResultPageResponseDTO<MovieResponseDTO>> getMoviesByCinema(
            @PathVariable @NotNull(message = "Cinema code is required") String cinemaCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(movieService.getMoviesByCinemaCode(cinemaCode, pageable));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailResponseDTO> getMovieDetail(
            @PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.getMovieDetail(movieId));
    }
} 
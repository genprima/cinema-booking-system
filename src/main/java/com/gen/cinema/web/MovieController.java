package com.gen.cinema.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gen.cinema.dto.response.MovieResponseDTO;
import com.gen.cinema.dto.response.MovieDetailResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.dto.request.UpdateMovieRequestDTO;
import com.gen.cinema.service.MovieService;
import com.gen.cinema.service.FileService;
import com.gen.cinema.validation.ValidImageFilename;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import com.gen.cinema.util.PaginationUtil;
import com.gen.cinema.dto.response.MovieListResponseDTO;

@RestController
@RequestMapping("/v1/movies")
@Validated
public class MovieController {

    private final MovieService movieService;
    private final FileService fileService;

    public MovieController(MovieService movieService, FileService fileService) {
        this.movieService = movieService;
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<ResultPageResponseDTO<MovieListResponseDTO>> getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(PaginationUtil.getDirection(direction), sortBy));
        return ResponseEntity.ok(movieService.getMovies(title, pageable));
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

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDetailResponseDTO> updateMovie(
            @PathVariable Long movieId,
            @Valid @RequestBody UpdateMovieRequestDTO request) {
        
        return ResponseEntity.ok(movieService.updateMovie(movieId, request));
    }

    @PutMapping("/{movieId}/presigned-url")
    public ResponseEntity<String> getMovieImageUploadUrl(
            @PathVariable @NotNull(message = "Movie ID is required") Long movieId,
            @RequestParam @ValidImageFilename String filename) {
        
        movieService.getMovieDetail(movieId);
        String objectPath = "movie/" + movieId + "/" + filename;
        String presignedUrl = fileService.generatePresignedUploadUrl(objectPath, 600);
        return ResponseEntity.ok(presignedUrl);
    }

    @PutMapping("/{movieId}/image")
    public ResponseEntity<Void> updateMovieImage(
            @PathVariable @NotNull(message = "Movie ID is required") Long movieId,
            @RequestParam @ValidImageFilename String filename) {
        
        movieService.updateMovieImage(movieId, filename);
        return ResponseEntity.ok().build();
    }
} 
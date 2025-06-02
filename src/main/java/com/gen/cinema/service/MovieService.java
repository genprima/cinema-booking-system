package com.gen.cinema.service;

import org.springframework.data.domain.Pageable;
import com.gen.cinema.dto.response.MovieResponseDTO;
import com.gen.cinema.dto.response.MovieDetailResponseDTO;
import com.gen.cinema.dto.request.UpdateMovieRequestDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;

public interface MovieService {
    ResultPageResponseDTO<MovieResponseDTO> getMoviesByCinemaCode(String cinemaCode, Pageable pageable);
    MovieDetailResponseDTO getMovieDetail(Long movieId);
    MovieDetailResponseDTO updateMovie(Long movieId, UpdateMovieRequestDTO request);
    void updateMovieImage(Long movieId, String filename);
} 
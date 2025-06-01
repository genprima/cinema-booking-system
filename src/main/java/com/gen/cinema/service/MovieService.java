package com.gen.cinema.service;

import org.springframework.data.domain.Pageable;
import com.gen.cinema.dto.response.MovieResponseDTO;
import com.gen.cinema.dto.response.MovieDetailResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;

public interface MovieService {
    ResultPageResponseDTO<MovieResponseDTO> getMoviesByCinemaCode(String cinemaCode, Pageable pageable);
    MovieDetailResponseDTO getMovieDetail(Long movieId);
} 
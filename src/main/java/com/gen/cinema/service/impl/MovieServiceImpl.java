package com.gen.cinema.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gen.cinema.domain.Movie;
import com.gen.cinema.dto.response.MovieResponseDTO;
import com.gen.cinema.dto.response.MovieDetailResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.repository.MovieScheduleRepository;
import com.gen.cinema.repository.MovieRepository;
import com.gen.cinema.service.MovieService;
import com.gen.cinema.util.PaginationUtil;
import com.gen.cinema.exception.BadRequestAlertException;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieScheduleRepository movieScheduleRepository;
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieScheduleRepository movieScheduleRepository, MovieRepository movieRepository) {
        this.movieScheduleRepository = movieScheduleRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public ResultPageResponseDTO<MovieResponseDTO> getMoviesByCinemaCode(String cinemaCode, Pageable pageable) {
        var moviePage = movieScheduleRepository.findDistinctMoviesByCinemaId(cinemaCode, pageable);
        return PaginationUtil.createResultPageDTO(
            moviePage.getContent(),
            moviePage.getTotalElements(),
            moviePage.getTotalPages()
        );
    }

    @Override
    public MovieDetailResponseDTO getMovieDetail(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new BadRequestAlertException("Movie not found"));
        return new MovieDetailResponseDTO(
            movie.getId(),
            movie.getTitle(),
            movie.getDescription(),
            movie.getSynopsis(),
            movie.getRating(),
            movie.getDuration(),
            movie.getImageUrl()
        );
    }
} 
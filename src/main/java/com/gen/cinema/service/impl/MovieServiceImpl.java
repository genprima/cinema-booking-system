package com.gen.cinema.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gen.cinema.dto.response.MovieResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.repository.MovieScheduleRepository;
import com.gen.cinema.service.MovieService;
import com.gen.cinema.util.PaginationUtil;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieScheduleRepository movieScheduleRepository;

    public MovieServiceImpl(MovieScheduleRepository movieScheduleRepository) {
        this.movieScheduleRepository = movieScheduleRepository;
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
} 
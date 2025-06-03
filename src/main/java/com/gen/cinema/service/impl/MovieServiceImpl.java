package com.gen.cinema.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gen.cinema.domain.Movie;
import com.gen.cinema.dto.response.MovieResponseDTO;
import com.gen.cinema.dto.response.MovieDetailResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.repository.MovieScheduleRepository;
import com.gen.cinema.repository.MovieRepository;
import com.gen.cinema.service.FileService;
import com.gen.cinema.service.MovieService;
import com.gen.cinema.util.PaginationUtil;

import jakarta.transaction.Transactional;

import com.gen.cinema.exception.BadRequestAlertException;
import com.gen.cinema.dto.request.UpdateMovieRequestDTO;
import com.gen.cinema.dto.response.MovieListResponseDTO;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieScheduleRepository movieScheduleRepository;
    private final MovieRepository movieRepository;
    private final FileService fileService;

    public MovieServiceImpl(MovieScheduleRepository movieScheduleRepository, MovieRepository movieRepository, FileService fileService) {
        this.movieScheduleRepository = movieScheduleRepository;
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public ResultPageResponseDTO<MovieListResponseDTO> getMovies(String title, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findMoviesByTitleContainingIgnoreCase(title, pageable);
        return PaginationUtil.createResultPageDTO(
            moviePage.getContent().stream()
                .map(movie -> new MovieListResponseDTO(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDescription(),
                    movie.getDuration(),
                    movie.getCreatedBy(),
                    movie.getModifiedBy(),
                    movie.getCreatedDate(),
                    movie.getModifiedDate()
                ))
                .toList(),
            moviePage.getTotalElements(),
            moviePage.getTotalPages()
        );
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
            
        String imageUrl = null;
        if (movie.getImageUrl() != null) {
            // Generate a presigned download URL that expires in 1 hour
            imageUrl = fileService.generatePresignedDownloadUrl(movie.getImageUrl());
        }
        
        return new MovieDetailResponseDTO(
            movie.getId(),
            movie.getTitle(),
            movie.getDescription(),
            movie.getSynopsis(),
            movie.getRating(),
            movie.getDuration(),
            imageUrl
        );
    }

    @Override
    @Transactional
    public MovieDetailResponseDTO updateMovie(Long movieId, UpdateMovieRequestDTO request) {
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new BadRequestAlertException("Movie not found"));
        
        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setSynopsis(request.synopsis());
        movie.setRating(request.rating());
        movie.setDirector(request.director());
        movie.setDuration(request.duration());
        movieRepository.save(movie);

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

    @Override
    @Transactional
    public void updateMovieImage(Long movieId, String filename) {
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new BadRequestAlertException("Movie not found"));

        String imageUrl = "movie/" + movieId + "/" + filename;
        if (!fileService.fileExists(imageUrl)) {
            throw new BadRequestAlertException("File not found, please reupload the file");
        }
        
        movie.setImageUrl(imageUrl);
        movieRepository.save(movie);
    }
} 
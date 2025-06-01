package com.gen.cinema.service;

import org.springframework.data.domain.Pageable;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.dto.response.StudioResponseDTO;

public interface StudioService {
    ResultPageResponseDTO<StudioResponseDTO> getStudiosByCinemaCodeAndMovieId(
        String cinemaCode, 
        Long movieId, 
        Pageable pageable);
} 
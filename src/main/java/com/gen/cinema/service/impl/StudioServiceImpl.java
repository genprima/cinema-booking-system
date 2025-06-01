package com.gen.cinema.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.dto.response.StudioResponseDTO;
import com.gen.cinema.repository.StudioRepository;
import com.gen.cinema.service.StudioService;
import com.gen.cinema.util.PaginationUtil;

@Service
public class StudioServiceImpl implements StudioService {

    private final StudioRepository studioRepository;

    public StudioServiceImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Override
    public ResultPageResponseDTO<StudioResponseDTO> getStudiosByCinemaCodeAndMovieId(
            String cinemaCode, 
            Long movieId, 
            Pageable pageable) {
        Page<StudioResponseDTO> studioPage = studioRepository.findStudiosByCinemaCodeAndMovieId(cinemaCode, movieId, pageable);
        return PaginationUtil.createResultPageDTO(
            studioPage.getContent(),
            studioPage.getTotalElements(),
            studioPage.getTotalPages()
        );
    }
} 
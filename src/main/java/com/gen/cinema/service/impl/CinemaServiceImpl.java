package com.gen.cinema.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gen.cinema.dto.response.CinemaResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.repository.CityCinemaRepository;
import com.gen.cinema.service.CinemaService;
import com.gen.cinema.util.PaginationUtil;

@Service
public class CinemaServiceImpl implements CinemaService {
    
    private final CityCinemaRepository cityCinemaRepository;

    public CinemaServiceImpl(CityCinemaRepository cityCinemaRepository) {
        this.cityCinemaRepository = cityCinemaRepository;
    }

    @Override
    public ResultPageResponseDTO<CinemaResponseDTO> getCinemasByCityCode(String cityCode, String cinemaName, int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = PaginationUtil.getDirection(direction);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<CinemaResponseDTO> cinemaPage = cityCinemaRepository.findCinemasByCityCode(cityCode, cinemaName, pageRequest);

        return PaginationUtil.createResultPageDTO(
            cinemaPage.getContent(),
            cinemaPage.getTotalElements(),
            cinemaPage.getTotalPages());
    }
} 
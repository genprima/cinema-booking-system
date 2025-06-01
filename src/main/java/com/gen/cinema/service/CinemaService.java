package com.gen.cinema.service;

import com.gen.cinema.dto.response.CinemaResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;

public interface CinemaService {
    ResultPageResponseDTO<CinemaResponseDTO> getCinemasByCityCode(String cityCode, String cinemaName, int page, int size, String sortBy, String direction);
} 
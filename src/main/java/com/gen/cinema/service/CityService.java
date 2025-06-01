package com.gen.cinema.service;

import com.gen.cinema.dto.response.CityResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;

public interface CityService {
    ResultPageResponseDTO<CityResponseDTO> getCities(String name, int page, int size, String sortBy, String direction);
} 
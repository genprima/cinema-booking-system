package com.gen.cinema.service.impl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gen.cinema.domain.City;
import com.gen.cinema.dto.response.CityResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.repository.CityRepository;
import com.gen.cinema.service.CityService;

import com.gen.cinema.util.PaginationUtil;

@Service
public class CityServiceImpl implements CityService {
    
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public ResultPageResponseDTO<CityResponseDTO> getCities(String search, int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = PaginationUtil.getDirection(direction);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<City> cityPage = cityRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(search, search, pageRequest);

        List<CityResponseDTO> cityDTOs = cityPage.getContent().stream()
                .map(city -> new CityResponseDTO(
                    city.getId(),
                    city.getName(),
                    city.getCode()))
                .toList();

        return PaginationUtil.createResultPageDTO(
            cityDTOs,
            cityPage.getTotalElements(),
            cityPage.getTotalPages());
    }
} 
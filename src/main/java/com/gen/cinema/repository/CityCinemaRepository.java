package com.gen.cinema.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.CityCinema;
import com.gen.cinema.dto.response.CinemaResponseDTO;

@Repository
public interface CityCinemaRepository extends JpaRepository<CityCinema, Long> {
    
    @Query("SELECT new com.gen.cinema.dto.response.CinemaResponseDTO(c.id, c.cinemaCode, c.name, c.address, c.phone) " +
           "FROM CityCinema cc " +
           "JOIN cc.cinema c ON c.id = cc.cinema.id " +
           "JOIN cc.city city ON city.id = cc.city.id " +
           "WHERE city.code = :cityCode " +
           "AND (COALESCE(:cinemaName, '') = '' OR c.name LIKE CONCAT('%', :cinemaName, '%'))")
    Page<CinemaResponseDTO> findCinemasByCityCode(@Param("cityCode") String cityCode, @Param("cinemaName") String cinemaName, Pageable pageable);
} 
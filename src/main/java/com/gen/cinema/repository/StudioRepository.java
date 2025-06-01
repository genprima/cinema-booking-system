package com.gen.cinema.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.Studio;
import com.gen.cinema.dto.response.StudioResponseDTO;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {
    
    @Query("SELECT DISTINCT new com.gen.cinema.dto.response.StudioResponseDTO(" +
           "s.id, s.name, sl.name, sl.maxRows, sl.maxColumns) " +
           "FROM Studio s " +
           "INNER JOIN StudioLayout sl ON sl = s.studioLayout " +
           "INNER JOIN CityCinema cc ON cc = s.cityCinema " +
           "INNER JOIN Cinema c ON c = cc.cinema " +
           "INNER JOIN MovieSchedule ms ON ms.studio = s " +
           "INNER JOIN Movie m ON m = ms.movie " +
           "WHERE c.cinemaCode = :cinemaCode " +
           "AND m.id = :movieId " +
           "ORDER BY s.name ASC")
    Page<StudioResponseDTO> findStudiosByCinemaCodeAndMovieId(
        @Param("cinemaCode") String cinemaCode, 
        @Param("movieId") Long movieId, 
        Pageable pageable);
} 
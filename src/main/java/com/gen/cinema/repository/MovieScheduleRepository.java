package com.gen.cinema.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.MovieSchedule;
import com.gen.cinema.dto.response.MovieResponseDTO;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> {
    
    @Query("SELECT DISTINCT new com.gen.cinema.dto.response.MovieResponseDTO(" +
           "m.id, m.title, m.description, m.duration, m.imageUrl) " +
           "FROM MovieSchedule ms " +
           "INNER JOIN Movie m ON m = ms.movie " +
           "INNER JOIN Studio s ON s = ms.studio " +
           "INNER JOIN CityCinema cc ON cc = s.cityCinema " +
           "INNER JOIN Cinema c ON c = cc.cinema " +
           "WHERE c.cinemaCode = :cinemaCode " +
           "ORDER BY m.title ASC")
    Page<MovieResponseDTO> findDistinctMoviesByCinemaId(@Param("cinemaCode") String cinemaCode, Pageable pageable);
} 
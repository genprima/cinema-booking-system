package com.gen.cinema.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.MovieSchedule;
import com.gen.cinema.dto.response.MovieResponseDTO;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    
    @Query("SELECT DISTINCT DATE(ms.startTime) " +
           "FROM MovieSchedule ms " +
           "WHERE ms.studio.id = :studioId " +
           "AND ms.movie.id = :movieId " +
           "AND ms.startTime >= :startOfToday " +
           "ORDER BY DATE(ms.startTime) ASC")
    List<Date> findDistinctScheduleDatesByStudioMovie(
        @Param("studioId") Long studioId,
        @Param("movieId") Long movieId,
        @Param("startOfToday") LocalDateTime startOfToday
    );

    @Query("SELECT ms FROM MovieSchedule ms " +
           "WHERE ms.studio.id = :studioId " +
           "AND ms.movie.id = :movieId " +
           "AND DATE(ms.startTime) = :date " +
           "ORDER BY ms.startTime ASC")
    List<MovieSchedule> findSchedulesByStudioMovieAndDate(
        @Param("studioId") Long studioId,
        @Param("movieId") Long movieId,
        @Param("date") LocalDate date
    );
} 
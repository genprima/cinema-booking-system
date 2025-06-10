package com.gen.cinema.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.MovieSchedule;
import com.gen.cinema.dto.response.MovieResponseDTO;
import com.gen.cinema.projection.ScheduleSeatProjection;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Query("SELECT " +
           "mss.secureId as id, " +
           "ss.row as row, " +
           "ss.number as number, " +
           "ss.xCoordinate as x, " +
           "ss.yCoordinate as y, " +
           "mss.status as status, " +
           "ms.price + s.additionalPrice as price, " +
           "s.seatType as seatType " +
           "FROM MovieScheduleSeat mss " +
           "JOIN mss.movieSchedule ms " +
           "JOIN mss.studioSeat ss " +
           "JOIN ss.seat s " +
           "WHERE ms.secureId = :scheduleId " +
           "ORDER BY ss.yCoordinate ASC, ss.xCoordinate ASC")
    List<ScheduleSeatProjection> findScheduleSeatsByScheduleId(@Param("scheduleId") UUID scheduleId);

    Optional<MovieSchedule> findBySecureId(UUID secureId);
} 
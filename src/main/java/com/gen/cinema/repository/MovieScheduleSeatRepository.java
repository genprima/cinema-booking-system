package com.gen.cinema.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.MovieSchedule;
import com.gen.cinema.domain.MovieScheduleSeat;
import com.gen.cinema.enums.SeatStatus;

@Repository
public interface MovieScheduleSeatRepository extends JpaRepository<MovieScheduleSeat, Long> {
    List<MovieScheduleSeat> findAllBySecureIdIn(List<UUID> secureIds);
    
    @Query("SELECT mss FROM MovieScheduleSeat mss " +
           "JOIN mss.studioSeat ss " +
           "WHERE mss.movieSchedule = :movieSchedule " +
           "AND ss.row = :row " +
           "AND ss.number = :number")
    MovieScheduleSeat findByMovieScheduleAndRowAndNumber(
        @Param("movieSchedule") MovieSchedule movieSchedule,
        @Param("row") String row,
        @Param("number") Integer number
    );

    @Query("SELECT COUNT(mss) FROM MovieScheduleSeat mss " +
           "WHERE mss.secureId IN :secureIds " +
           "AND mss.status != :status")
    long countBySecureIdInAndStatusNot(
        @Param("secureIds") List<UUID> secureIds,
        @Param("status") SeatStatus status
    );

    @Query("SELECT mss FROM MovieScheduleSeat mss " +
           "WHERE mss.movieSchedule = :movieSchedule " +
           "AND mss.studioSeat.row = :row " +
           "AND mss.studioSeat.number IN :numbers " +
           "AND mss.status = :status")
    List<MovieScheduleSeat> findByMovieScheduleAndRowAndNumbersAndStatus(
        @Param("movieSchedule") MovieSchedule movieSchedule,
        @Param("row") String row,
        @Param("numbers") List<Integer> numbers,
        @Param("status") SeatStatus status
    );

    @Query("SELECT COUNT(mss) FROM MovieScheduleSeat mss " +
           "WHERE mss.movieSchedule = :movieSchedule " +
           "AND mss.studioSeat.row = :row " +
           "AND mss.studioSeat.number IN :numbers " +
           "AND mss.status = :status")
    long countByMovieScheduleAndRowAndNumbersAndStatus(
        @Param("movieSchedule") MovieSchedule movieSchedule,
        @Param("row") String row,
        @Param("numbers") List<Integer> numbers,
        @Param("status") SeatStatus status
    );

    @Query("SELECT COUNT(mss) FROM MovieScheduleSeat mss " +
           "WHERE mss.movieSchedule = :movieSchedule " +
           "AND mss.studioSeat.row = :row " +
           "AND mss.studioSeat.number = :number " +
           "AND mss.status = :status")
    long countByMovieScheduleAndRowAndNumberAndStatus(
        @Param("movieSchedule") MovieSchedule movieSchedule,
        @Param("row") String row,
        @Param("number") Integer number,
        @Param("status") SeatStatus status
    );

    @Query("SELECT COUNT(mss) FROM MovieScheduleSeat mss " +
           "WHERE mss.movieSchedule = :movieSchedule " +
           "AND mss.studioSeat.row = :row " +
           "AND mss.studioSeat.number = :number " +
           "AND mss.status != :status")
    long countByMovieScheduleAndRowAndNumberAndStatusNot(
        @Param("movieSchedule") MovieSchedule movieSchedule,
        @Param("row") String row,
        @Param("number") Integer number,
        @Param("status") SeatStatus status
    );
} 
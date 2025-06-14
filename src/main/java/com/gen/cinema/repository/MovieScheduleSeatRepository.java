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

    @Query("SELECT COUNT(mss) FROM MovieScheduleSeat mss " +
           "JOIN mss.studioSeat ss " +
           "WHERE mss.secureId IN :secureIds " +
           "AND EXISTS (" +
           "   SELECT 1 FROM MovieScheduleSeat mss2 " +
           "   JOIN mss2.studioSeat ss2 " +
           "   WHERE mss2.movieSchedule = mss.movieSchedule " +
           "   AND ss2.row = ss.row " +
           "   AND ss2.xCoordinate = ss.xCoordinate - 1 " +  // x-1 position
           "   AND mss2.status = 'AVAILABLE' " +
           "   AND EXISTS (" +
           "       SELECT 1 FROM MovieScheduleSeat mss3 " +
           "       JOIN mss3.studioSeat ss3 " +
           "       WHERE mss3.movieSchedule = mss.movieSchedule " +
           "       AND ss3.row = ss.row " +
           "       AND ss3.xCoordinate = ss.xCoordinate - 2 " +  // x-2 position
           "       AND mss3.status != 'AVAILABLE'" +
           "   )" +
           ")")
    long countBySecureIdsWithPattern(@Param("secureIds") List<UUID> secureIds);

    @Query("SELECT COUNT(DISTINCT mss.movieSchedule) FROM MovieScheduleSeat mss " +
           "WHERE mss.secureId IN :secureIds")
    long countDistinctMovieSchedules(@Param("secureIds") List<UUID> secureIds);
} 
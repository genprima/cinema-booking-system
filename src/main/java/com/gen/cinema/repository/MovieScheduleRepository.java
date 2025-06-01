package com.gen.cinema.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.MovieSchedule;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> {
    @Query("SELECT ms FROM MovieSchedule ms WHERE ms.movie.id = :movieId " +
           "AND (:studioId IS NULL OR ms.studio.id = :studioId) " +
           "AND ms.startTime BETWEEN :start AND :end")
    List<MovieSchedule> findSchedules(@Param("movieId") Long movieId,
                                      @Param("studioId") Long studioId,
                                      @Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);
} 
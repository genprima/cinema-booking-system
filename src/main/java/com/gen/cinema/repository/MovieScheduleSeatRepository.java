package com.gen.cinema.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.MovieScheduleSeat;

@Repository
public interface MovieScheduleSeatRepository extends JpaRepository<MovieScheduleSeat, Long> {
    @Query("SELECT mss FROM MovieScheduleSeat mss WHERE mss.movieSchedule.secureId = :scheduleId ORDER BY mss.studioSeat.xCoordinate, mss.studioSeat.yCoordinate")
    List<MovieScheduleSeat> findByScheduleSecureIdOrdered(@Param("scheduleId") UUID scheduleId);
} 
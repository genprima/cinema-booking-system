package com.gen.cinema.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.Studio;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {
    @Query("SELECT s FROM Studio s WHERE s.cityCinema.cinema.id = :cinemaId")
    List<Studio> findByCinemaId(@Param("cinemaId") Long cinemaId);
} 
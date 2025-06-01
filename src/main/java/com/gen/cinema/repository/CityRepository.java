package com.gen.cinema.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
    @Query("SELECT DISTINCT c FROM City c " +
           "JOIN c.cityCinemas cc " +
           "JOIN cc.cinema ci " +
           "JOIN cc.studios s " +
           "JOIN s.movieSchedules ms " +
           "WHERE c.id = :cityId " +
           "AND ms.startTime > CURRENT_TIMESTAMP")
    List<City> findCitiesWithActiveMovies(@Param("cityId") Long cityId);
} 
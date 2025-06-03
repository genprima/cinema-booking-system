package com.gen.cinema.repository;

import com.gen.cinema.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitle(String title);
    Page<Movie> findMoviesByTitleContainingIgnoreCase(String title, Pageable pageable);
} 
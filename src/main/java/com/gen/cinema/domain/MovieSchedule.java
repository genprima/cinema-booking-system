package com.gen.cinema.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Table(name = "movie_schedule", indexes = {
    @Index(name = "idx_movie_schedule_start_time", columnList = "start_time"),
    @Index(name = "idx_movie_schedule_movie_id", columnList = "movie_id"),
    @Index(name = "idx_movie_schedule_studio_id", columnList = "studio_id")
})
public class MovieSchedule extends AbstractBaseUUIDEntity {

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
} 
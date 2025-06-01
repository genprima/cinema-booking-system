package com.gen.cinema.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "studio")
public class Studio extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_cinema_id", nullable = false)
    private CityCinema cityCinema;

    @ManyToOne
    @JoinColumn(name = "studio_layout_id", nullable = false)
    private StudioLayout studioLayout;

    @OneToMany(mappedBy = "studio")
    private Set<MovieSchedule> movieSchedules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityCinema getCityCinema() {
        return cityCinema;
    }

    public void setCityCinema(CityCinema cityCinema) {
        this.cityCinema = cityCinema;
    }

    public StudioLayout getStudioLayout() {
        return studioLayout;
    }

    public void setStudioLayout(StudioLayout studioLayout) {
        this.studioLayout = studioLayout;
    }

    public Set<MovieSchedule> getMovieSchedules() {
        return movieSchedules;
    }

    public void setMovieSchedules(Set<MovieSchedule> movieSchedules) {
        this.movieSchedules = movieSchedules;
    }
}

package com.gen.cinema.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Table(name = "cinema", indexes = {
    @Index(name = "idx_cinema_code", columnList = "cinema_code")
})
public class Cinema extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "cinema_code", nullable = false, unique = true)
    private String cinemaCode;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "cinema")
    private Set<CityCinema> cityCinemas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<CityCinema> getCityCinemas() {
        return cityCinemas;
    }

    public void setCityCinemas(Set<CityCinema> cityCinemas) {
        this.cityCinemas = cityCinemas;
    }
}

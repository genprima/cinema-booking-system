package com.gen.cinema.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_seat")
public class BookingSeat extends AbstractBaseUUIDEntity {

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "movie_schedule_seat_id", nullable = false)
    private MovieScheduleSeat movieScheduleSeat;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public MovieScheduleSeat getMovieScheduleSeat() {
        return movieScheduleSeat;
    }

    public void setMovieScheduleSeat(MovieScheduleSeat movieScheduleSeat) {
        this.movieScheduleSeat = movieScheduleSeat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
} 
package com.gen.cinema.domain;

import java.math.BigDecimal;

import com.gen.cinema.enums.SeatStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie_schedule_seat")
public class MovieScheduleSeat extends AbstractBaseUUIDEntity {

    @ManyToOne
    @JoinColumn(name = "movie_schedule_id", nullable = false)
    private MovieSchedule movieSchedule;

    @ManyToOne
    @JoinColumn(name = "studio_seat_id", nullable = false)
    private StudioSeat studioSeat;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @Column(name = "additional_price", nullable = false)
    private BigDecimal additionalPrice;

    public MovieSchedule getMovieSchedule() {
        return movieSchedule;
    }

    public void setMovieSchedule(MovieSchedule movieSchedule) {
        this.movieSchedule = movieSchedule;
    }

    public StudioSeat getStudioSeat() {
        return studioSeat;
    }

    public void setStudioSeat(StudioSeat studioSeat) {
        this.studioSeat = studioSeat;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return movieSchedule.getPrice().add(additionalPrice);
    }

    public void setAdditionalPrice(BigDecimal additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public BigDecimal getAdditionalPrice() {
        return additionalPrice;
    }
} 
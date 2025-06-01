package com.gen.cinema.domain;

import java.math.BigDecimal;

import com.gen.cinema.enums.SeatType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "seat")
public class Seat extends AbstractBaseEntity {

    @Column(name = "seat_type", columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Column(name = "additional_price", nullable = false)
    private BigDecimal additionalPrice = BigDecimal.ZERO;

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public BigDecimal getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(BigDecimal additionalPrice) {
        this.additionalPrice = additionalPrice;
    }   
}

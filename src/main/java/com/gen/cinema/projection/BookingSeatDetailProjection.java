package com.gen.cinema.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface BookingSeatDetailProjection {
    UUID getBookingId();
    Long getSeatId();
    String getRow();
    Integer getNumber();
    String getSeatType();
    BigDecimal getPrice();
} 
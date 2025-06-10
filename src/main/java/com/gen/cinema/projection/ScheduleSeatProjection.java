package com.gen.cinema.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface ScheduleSeatProjection {
    UUID getId();
    String getRow();
    Integer getNumber();
    Integer getX();
    Integer getY();
    String getStatus();
    BigDecimal getPrice();
    String getSeatType();
} 
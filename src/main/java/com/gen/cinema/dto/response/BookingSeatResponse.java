package com.gen.cinema.dto.response;

import java.math.BigDecimal;

public record BookingSeatResponse(
    Long id,
    String row,
    Integer number,
    Integer x,
    Integer y,
    String status,
    BigDecimal price,
    String seatType
) {} 
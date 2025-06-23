package com.gen.cinema.dto.response;

import java.math.BigDecimal;

public record BookingSeatDetailDTO(
    String seatId,
    String row,
    String number,
    String seatType,
    BigDecimal price
) {} 
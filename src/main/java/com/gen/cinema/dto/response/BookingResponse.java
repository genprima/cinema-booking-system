package com.gen.cinema.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record BookingResponse(
    String id,
    String bookingCode,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime transactionDate,
    String status,
    BigDecimal totalAmount,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime paymentDeadline,
    List<String> seats
) {} 
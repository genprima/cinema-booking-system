package com.gen.cinema.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingListResponseDTO(
    String id,
    String bookingCode,
    String movieTitle,
    String bookingStatus,
    LocalDateTime scheduleTime,
    String userEmail,
    BigDecimal totalAmount,
    LocalDateTime paymentDeadline
) {} 
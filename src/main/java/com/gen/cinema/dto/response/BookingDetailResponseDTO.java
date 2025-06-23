package com.gen.cinema.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingDetailResponseDTO(
    String id,
    String bookingCode,
    String movieTitle,
    List<BookingSeatDetailDTO> seats,
    String bookingStatus,
    LocalDateTime scheduleTime,
    String userEmail,
    BigDecimal totalAmount,
    LocalDateTime paymentDeadline,
    LocalDateTime transactionDate,
    LocalDateTime paymentDate
) {} 
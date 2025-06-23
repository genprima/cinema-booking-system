package com.gen.cinema.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface BookingDetailProjection {
    UUID getSecureId();
    String getBookingCode();
    String getMovieTitle();
    String getStatus();
    LocalDateTime getScheduleStartTime();
    String getUserEmail();
    BigDecimal getTotalAmount();
    LocalDateTime getPaymentDeadline();
    LocalDateTime getTransactionDate();
    LocalDateTime getPaymentDate();
} 
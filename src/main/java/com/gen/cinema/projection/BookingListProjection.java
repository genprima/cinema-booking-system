package com.gen.cinema.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface BookingListProjection {
    UUID getSecureId();
    String getBookingCode();
    String getStatus();
    LocalDateTime getTransactionDate();
    LocalDateTime getScheduleStartTime();
    String getMovieTitle();
    String getUserEmail();
    String getUserName();
    BigDecimal getTotalAmount();
    LocalDateTime getPaymentDeadline();
} 
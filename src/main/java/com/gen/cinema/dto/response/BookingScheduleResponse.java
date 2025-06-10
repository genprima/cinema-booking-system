package com.gen.cinema.dto.response;

import java.util.List;

public record BookingScheduleResponse(
    String secureId,
    String startTime,
    List<BookingSeatResponse> seats
) {} 
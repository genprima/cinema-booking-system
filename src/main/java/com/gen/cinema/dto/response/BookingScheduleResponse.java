package com.gen.cinema.dto.response;

import java.util.List;
 
public record BookingScheduleResponse(
    String scheduleId,
    String startTime,
    List<BookingSeatResponse> seats
) {} 
package com.gen.cinema.service;

import com.gen.cinema.dto.request.BookingRequest;
import com.gen.cinema.dto.response.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
} 
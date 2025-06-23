package com.gen.cinema.service;

import com.gen.cinema.dto.request.BookingRequest;
import com.gen.cinema.dto.response.BookingListResponseDTO;
import com.gen.cinema.dto.response.BookingResponse;
import com.gen.cinema.dto.response.BookingDetailResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    
    ResultPageResponseDTO<BookingListResponseDTO> getBookingList(
        String movieTitle, 
        String bookingCode, 
        String scheduleId, 
        int page,
        int size,
        String sortBy,
        String direction
    );
    
    BookingDetailResponseDTO getBookingDetail(String bookingId);
} 
package com.gen.cinema.web;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;

import com.gen.cinema.dto.request.BookingRequest;
import com.gen.cinema.dto.response.BookingListResponseDTO;
import com.gen.cinema.dto.response.BookingResponse;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.dto.response.BookingDetailResponseDTO;
import com.gen.cinema.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/booking")
@Validated
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody @Valid BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }
    
    @GetMapping
    public ResponseEntity<ResultPageResponseDTO<BookingListResponseDTO>> getBookingList(
            @RequestParam(required = false) String movieTitle,
            @RequestParam(required = false) String bookingCode,
            @RequestParam(required = false) String scheduleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        ResultPageResponseDTO<BookingListResponseDTO> bookings = bookingService.getBookingList(
            movieTitle, bookingCode, scheduleId, page, size, sortBy, direction
        );
        
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{booking_id}")
    public ResponseEntity<BookingDetailResponseDTO> getBookingDetail(
            @PathVariable("booking_id") String bookingId) {
        BookingDetailResponseDTO bookingDetail = bookingService.getBookingDetail(bookingId);
        return ResponseEntity.ok(bookingDetail);
    }

    @PatchMapping("/{booking_id}/pay")
    public ResponseEntity<Boolean> payBooking(@PathVariable("booking_id") String bookingId) {
        return ResponseEntity.ok(bookingService.payBooking(bookingId));
    }
} 
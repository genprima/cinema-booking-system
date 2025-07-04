package com.gen.cinema.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.gen.cinema.service.BookingSchedulerService;
import com.gen.cinema.validation.annotation.CanAccessBooking;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/booking")
@Validated
@SecurityRequirement(name = "Bearer Authentication")
public class BookingController {

    private final BookingService bookingService;
    private final BookingSchedulerService bookingSchedulerService;

    public BookingController(BookingService bookingService, BookingSchedulerService bookingSchedulerService) {
        this.bookingService = bookingService;
        this.bookingSchedulerService = bookingSchedulerService;
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
            @PathVariable("booking_id") @CanAccessBooking String bookingId) {
        BookingDetailResponseDTO bookingDetail = bookingService.getBookingDetail(bookingId);
        return ResponseEntity.ok(bookingDetail);
    }

    @PatchMapping("/{booking_id}/pay")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> payBooking(@PathVariable("booking_id") String bookingId) {
        return ResponseEntity.ok(bookingService.payBooking(bookingId));
    }

    @PatchMapping("/{booking_id}/cancel")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable("booking_id") String bookingId) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }

    @PostMapping("/cancel-expired")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> triggerCancelExpiredBookings() {
        bookingSchedulerService.cancelExpiredBookings();
        return ResponseEntity.ok("Expired booking cancellation process triggered successfully");
    }
} 
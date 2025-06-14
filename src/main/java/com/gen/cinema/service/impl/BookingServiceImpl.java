package com.gen.cinema.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gen.cinema.domain.Booking;
import com.gen.cinema.domain.BookingSeat;
import com.gen.cinema.domain.MovieSchedule;
import com.gen.cinema.domain.MovieScheduleSeat;
import com.gen.cinema.domain.User;
import com.gen.cinema.dto.request.BookingRequest;
import com.gen.cinema.dto.response.BookingResponse;
import com.gen.cinema.enums.BookingStatus;
import com.gen.cinema.enums.SeatStatus;
import com.gen.cinema.repository.BookingRepository;
import com.gen.cinema.repository.MovieScheduleSeatRepository;
import com.gen.cinema.service.BookingService;
import com.gen.cinema.service.UserService;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final MovieScheduleSeatRepository movieScheduleSeatRepository;
    private final UserService userService;

    public BookingServiceImpl(
        BookingRepository bookingRepository, 
        MovieScheduleSeatRepository movieScheduleSeatRepository, 
        UserService userService
    ) {
        this.bookingRepository = bookingRepository;
        this.movieScheduleSeatRepository = movieScheduleSeatRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        
        User currentUser = userService.getCurrentUser();

        // Get all requested seats
        List<MovieScheduleSeat> requestedSeats = movieScheduleSeatRepository.findAllBySecureIdIn(
            request.scheduleSeatIds().stream()
                .map(UUID::fromString)
                .collect(Collectors.toList())
        );

        MovieScheduleSeat firstSeat = requestedSeats.get(0);
        MovieSchedule movieSchedule = firstSeat.getMovieSchedule();

        // Create booking
        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setMovieSchedule(movieSchedule);
        booking.setTransactionDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.WAITING_PAYMENT);
        booking.setPaymentDeadline(movieSchedule.getStartTime().minusHours(2));

        // Create booking seats using stream
        requestedSeats.stream()
            .peek(seat -> {
                seat.setStatus(SeatStatus.BOOKED);
                movieScheduleSeatRepository.save(seat);
            })
            .map(seat -> {
                BookingSeat bookingSeat = new BookingSeat();
                bookingSeat.setMovieScheduleSeat(seat);
                bookingSeat.setPrice(seat.getTotalPrice());
                return bookingSeat;
            })
            .forEach(booking::addBookingSeat);

        booking = bookingRepository.save(booking);

        // Convert to response
        return new BookingResponse(
            booking.getSecureId().toString(),
            booking.getBookingCode(),
            booking.getTransactionDate(),
            booking.getStatus().toString(),
            booking.getTotalAmount(),
            booking.getPaymentDeadline(),
            booking.getBookingSeats().stream()
                .map(bs -> bs.getMovieScheduleSeat().getStudioSeat().getRow() + 
                    bs.getMovieScheduleSeat().getStudioSeat().getNumber())
                .collect(Collectors.toList())
        );
    }
} 

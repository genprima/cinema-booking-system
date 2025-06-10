package com.gen.cinema.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
import com.gen.cinema.exception.BadRequestAlertException;
import com.gen.cinema.repository.BookingRepository;
import com.gen.cinema.repository.MovieScheduleSeatRepository;
import com.gen.cinema.service.BookingService;
import com.gen.cinema.service.UserService;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final MovieScheduleSeatRepository movieScheduleSeatRepository;
    private final UserService userService;


    public BookingServiceImpl(BookingRepository bookingRepository, MovieScheduleSeatRepository movieScheduleSeatRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.movieScheduleSeatRepository = movieScheduleSeatRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        // Get current user
        User currentUser = userService.getCurrentUser();

        // Get all requested seats
        List<MovieScheduleSeat> requestedSeats = movieScheduleSeatRepository.findAllBySecureIdIn(
            request.scheduleSeatIds().stream()
                .map(UUID::fromString)
                .collect(Collectors.toList())
        );

        if (requestedSeats.isEmpty()) {
            throw new BadRequestAlertException("No valid seats found");
        }

        MovieScheduleSeat firstSeat = requestedSeats.get(0);
        MovieSchedule movieSchedule = firstSeat.getMovieSchedule();

        // Validate all seats are from the same schedule
        if (!requestedSeats.stream().allMatch(seat -> 
            seat.getMovieSchedule().getId().equals(movieSchedule.getId()))) {
            throw new BadRequestAlertException("All seats must be from the same schedule");
        }

        // Validate seat availability and adjacent seats
        validateSeats(requestedSeats);

        // Create booking
        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setMovieSchedule(movieSchedule);
        booking.setTransactionDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.WAITING_PAYMENT);
        booking.setPaymentDeadline(movieSchedule.getStartTime().minusHours(2));

        // Create booking seats
        for (MovieScheduleSeat seat : requestedSeats) {
            // Update seat status
            seat.setStatus(SeatStatus.BOOKED);
            movieScheduleSeatRepository.save(seat);

            // Create booking seat
            BookingSeat bookingSeat = new BookingSeat();
            bookingSeat.setMovieScheduleSeat(seat);
            bookingSeat.setPrice(movieSchedule.getPrice().add(
                seat.getStudioSeat().getSeat().getAdditionalPrice()
            ));
            booking.addBookingSeat(bookingSeat);
        }

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

    private void validateSeats(List<MovieScheduleSeat> seats) {
        List<UUID> seatIds = seats.stream()
            .map(MovieScheduleSeat::getSecureId)
            .collect(Collectors.toList());
            
        long unavailableSeats = movieScheduleSeatRepository.countBySecureIdInAndStatusNot(
            seatIds, 
            SeatStatus.AVAILABLE
        );
        
        if (unavailableSeats > 0) {
            throw new BadRequestAlertException("One or more selected seats are not available");
        }

        // Group seats by row for adjacent seat validation
        Map<String, List<MovieScheduleSeat>> seatsByRow = seats.stream()
            .collect(Collectors.groupingBy(seat -> seat.getStudioSeat().getRow()));

        for (List<MovieScheduleSeat> rowSeats : seatsByRow.values()) {
            // Sort seats by x coordinate
            rowSeats.sort((a, b) -> a.getStudioSeat().getXCoordinate()
                .compareTo(b.getStudioSeat().getXCoordinate()));

            MovieScheduleSeat firstSeat = rowSeats.get(0);
            MovieSchedule movieSchedule = firstSeat.getMovieSchedule();
            String row = firstSeat.getStudioSeat().getRow();
            
            // Check if seat at -2 is not available and seat at -1 is available
            long seatBeforeBeforeNotAvailable = movieScheduleSeatRepository
                .countByMovieScheduleAndRowAndNumberAndStatusNot(
                    movieSchedule,
                    row,
                    firstSeat.getStudioSeat().getNumber() - 2,
                    SeatStatus.AVAILABLE
                );

            long seatBeforeAvailable = movieScheduleSeatRepository
                .countByMovieScheduleAndRowAndNumberAndStatus(
                    movieSchedule,
                    row,
                    firstSeat.getStudioSeat().getNumber() - 1,
                    SeatStatus.AVAILABLE
                );

            if (seatBeforeBeforeNotAvailable > 0 && seatBeforeAvailable > 0) {
                throw new BadRequestAlertException("Cannot book seat " + 
                    firstSeat.getStudioSeat().getRow() + 
                    firstSeat.getStudioSeat().getNumber() + 
                    " as it would leave a single available seat");
            }
        }
    }
} 

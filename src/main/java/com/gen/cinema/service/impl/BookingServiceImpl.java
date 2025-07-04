package com.gen.cinema.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gen.cinema.domain.Booking;
import com.gen.cinema.domain.BookingSeat;
import com.gen.cinema.domain.MovieSchedule;
import com.gen.cinema.domain.MovieScheduleSeat;
import com.gen.cinema.domain.User;
import com.gen.cinema.dto.request.BookingRequest;
import com.gen.cinema.dto.response.BookingResponse;
import com.gen.cinema.dto.response.BookingListResponseDTO;
import com.gen.cinema.dto.response.BookingDetailResponseDTO;
import com.gen.cinema.dto.response.ResultPageResponseDTO;
import com.gen.cinema.enums.BookingStatus;
import com.gen.cinema.enums.SeatStatus;
import com.gen.cinema.enums.UserRole;
import com.gen.cinema.projection.BookingListProjection;
import com.gen.cinema.projection.BookingDetailProjection;
import com.gen.cinema.projection.BookingSeatDetailProjection;
import com.gen.cinema.repository.BookingRepository;
import com.gen.cinema.repository.MovieScheduleSeatRepository;
import com.gen.cinema.service.BookingService;
import com.gen.cinema.service.UserService;
import com.gen.cinema.util.PaginationUtil;
import com.gen.cinema.exception.BadRequestAlertException;
import com.gen.cinema.dto.response.BookingSeatDetailDTO;

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

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime movieStartTime = movieSchedule.getStartTime();
        
        if (now.isAfter(movieStartTime) || now.isEqual(movieStartTime)) {
            throw new BadRequestAlertException("Cannot book seats for a movie that has already started");
        }

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
                seat.setStatus(SeatStatus.WAITING_PAYMENT);
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
                .collect(Collectors.toList()));
    }

    @Override
    public ResultPageResponseDTO<BookingListResponseDTO> getBookingList(
            String movieTitle, 
            String bookingCode, 
            String scheduleId, 
            int page,
            int size,
            String sortBy,
            String direction) {
        
        User currentUser = userService.getCurrentUser();
        
        Sort.Direction sortDirection = PaginationUtil.getDirection(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        String userEmail = (currentUser.getRole() == UserRole.USER) ? currentUser.getEmail() : null;
        
        Page<BookingListProjection> bookings = bookingRepository.findBookingsWithFilters(
                userEmail, movieTitle, bookingCode, scheduleId, pageable);
        
        List<BookingListResponseDTO> bookingDTOs = bookings.getContent().stream()
            .map(bookingData -> convertToBookingListResponseDTO(bookingData))
            .collect(Collectors.toList());
        
        return PaginationUtil.createResultPageDTO(
            bookingDTOs, 
            bookings.getTotalElements(), 
            bookings.getTotalPages());
    }

    @Override
    public BookingDetailResponseDTO getBookingDetail(String bookingId) {
        UUID bookingUuid = UUID.fromString(bookingId);
        
        BookingDetailProjection bookingDetail = bookingRepository.findBookingDetailById(bookingUuid);
        if (bookingDetail == null) {
            throw new BadRequestAlertException("Booking not found");
        }
        
        List<BookingSeatDetailProjection> seatDetails = bookingRepository.findSeatDetailsByBookingId(bookingUuid);
        
        List<BookingSeatDetailDTO> seats = seatDetails.stream()
            .map(seat -> new BookingSeatDetailDTO(
                seat.getSeatId().toString(),
                seat.getRow(),
                seat.getNumber().toString(),
                seat.getSeatType(),
                seat.getPrice()
            ))
            .collect(Collectors.toList());
        
        return new BookingDetailResponseDTO(
            bookingDetail.getSecureId().toString(),
            bookingDetail.getBookingCode(),
            bookingDetail.getMovieTitle(),
            seats,
            bookingDetail.getStatus(),
            bookingDetail.getScheduleStartTime(),
            bookingDetail.getUserEmail(),
            bookingDetail.getTotalAmount(),
            bookingDetail.getPaymentDeadline(),
            bookingDetail.getTransactionDate(),
            bookingDetail.getPaymentDate()
        );
    }

    @Override
    public Boolean payBooking(String bookingId) {
      
        Booking booking = bookingRepository.findBySecureId(UUID.fromString(bookingId))
            .orElseThrow(() -> new BadRequestAlertException("Booking not found"));

        // Update booking status
        booking.setStatus(BookingStatus.PAID);
        booking.setPaymentDate(LocalDateTime.now());

        // Update all related MovieScheduleSeat to BOOKED
        for (BookingSeat bookingSeat : booking.getBookingSeats()) {
            bookingSeat.getMovieScheduleSeat().setStatus(SeatStatus.BOOKED);
        }

        bookingRepository.save(booking);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean cancelBooking(String bookingId) {
        User currentUser = userService.getCurrentUser();
        
        Booking booking = bookingRepository.findBySecureId(UUID.fromString(bookingId))
            .orElseThrow(() -> new BadRequestAlertException("Booking not found"));

        if (currentUser.getRole() != UserRole.ADMIN && !booking.getUser().getEmail().equals(currentUser.getEmail())) {
            throw new BadRequestAlertException("You can only cancel your own bookings");
        }

        if (booking.getStatus() != BookingStatus.WAITING_PAYMENT && booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestAlertException("Only pending or waiting payment bookings can be cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        for (BookingSeat bookingSeat : booking.getBookingSeats()) {
            bookingSeat.getMovieScheduleSeat().setStatus(SeatStatus.AVAILABLE);
        }

        bookingRepository.save(booking);
        return Boolean.TRUE;
    }

    private BookingListResponseDTO convertToBookingListResponseDTO(BookingListProjection projection) {
        return new BookingListResponseDTO(
            projection.getSecureId().toString(),
            projection.getBookingCode(),
            projection.getMovieTitle(),
            projection.getStatus(),
            projection.getScheduleStartTime(),
            projection.getUserEmail(),
            projection.getTotalAmount(),
            projection.getPaymentDeadline()
        );
    }
}

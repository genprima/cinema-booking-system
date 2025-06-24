package com.gen.cinema.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.Booking;
import com.gen.cinema.projection.BookingListProjection;
import com.gen.cinema.projection.BookingDetailProjection;
import com.gen.cinema.projection.BookingSeatDetailProjection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    List<Booking> findByUserEmailOrderByTransactionDateDesc(String email);
    
    @Query("SELECT b.secureId as secureId, b.bookingCode as bookingCode, b.status as status, b.transactionDate as transactionDate, " +
           "ms.startTime as scheduleStartTime, m.title as movieTitle, u.email as userEmail, u.username as userName, " +
           "b.totalAmount as totalAmount, b.paymentDeadline as paymentDeadline " +
           "FROM Booking b " +
           "JOIN User u ON u.id = b.user.id " +
           "JOIN MovieSchedule ms ON ms.id = b.movieSchedule.id " +
           "JOIN Movie m ON m.id = ms.movie.id " +
           "WHERE (:userEmail IS NULL OR u.email = :userEmail) " +
           "AND (:movieTitle IS NULL OR m.title LIKE %:movieTitle%) " +
           "AND (:bookingCode IS NULL OR b.bookingCode = :bookingCode) " +
           "AND (:scheduleId IS NULL OR ms.secureId = :scheduleId)")
    Page<BookingListProjection> findBookingsWithFilters(
        @Param("userEmail") String userEmail,
        @Param("movieTitle") String movieTitle,
        @Param("bookingCode") String bookingCode,
        @Param("scheduleId") String scheduleId,
        Pageable pageable
    );

    @Query("SELECT b.secureId as secureId, b.bookingCode as bookingCode, " +
           "m.title as movieTitle, b.status as status, " +
           "ms.startTime as scheduleStartTime, u.email as userEmail, " +
           "b.totalAmount as totalAmount, b.paymentDeadline as paymentDeadline, " +
           "b.transactionDate as transactionDate, b.paymentDate as paymentDate " +
           "FROM Booking b " +
           "JOIN b.movieSchedule ms " +
           "JOIN ms.movie m " +
           "JOIN b.user u " +
           "WHERE b.secureId = :bookingId")
    BookingDetailProjection findBookingDetailById(@Param("bookingId") UUID bookingId);

    @Query("SELECT b.secureId as bookingId, ss.id as seatId, " +
           "ss.row as row, ss.number as number, " +
           "s.seatType as seatType, bs.price as price " +
           "FROM Booking b " +
           "JOIN b.bookingSeats bs " +
           "JOIN bs.movieScheduleSeat mss " +
           "JOIN mss.studioSeat ss " +
           "JOIN ss.seat s " +
           "WHERE b.secureId = :bookingId " +
           "ORDER BY ss.row, ss.number")
    List<BookingSeatDetailProjection> findSeatDetailsByBookingId(@Param("bookingId") UUID bookingId);

    Optional<Booking> findBySecureId(UUID secureId);

    Optional<Booking> findBySecureIdAndUserEmail(UUID secureId, String userEmail);
} 
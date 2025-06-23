package com.gen.cinema.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.Booking;
import com.gen.cinema.projection.BookingListProjection;

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
} 
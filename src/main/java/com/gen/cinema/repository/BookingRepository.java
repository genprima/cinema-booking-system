package com.gen.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
} 
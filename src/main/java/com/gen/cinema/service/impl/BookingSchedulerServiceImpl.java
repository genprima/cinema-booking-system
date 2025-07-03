package com.gen.cinema.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gen.cinema.repository.BookingRepository;
import com.gen.cinema.service.BookingSchedulerService;

@Service
public class BookingSchedulerServiceImpl implements BookingSchedulerService {
    
    private static final Logger log = LoggerFactory.getLogger(BookingSchedulerServiceImpl.class);
    
    private final BookingRepository bookingRepository;
    
    public BookingSchedulerServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    
    @Override
    @Scheduled(fixedRate = 300000)
    @Transactional
    public void cancelExpiredBookings() {
        LocalDateTime currentTime = LocalDateTime.now();
        log.info("Starting scheduled task to cancel expired bookings at {}", currentTime);
        
        try {
            int cancelledBookings = bookingRepository.cancelExpiredBookings(currentTime);
            
            if (cancelledBookings == 0) {
                log.info("No expired bookings found to cancel");
                return;
            }
            
            log.info("Cancelled {} expired bookings", cancelledBookings);
            
            int releasedSeats = bookingRepository.releaseExpiredBookingSeats(currentTime);
            
            log.info("Released {} seats from cancelled bookings", releasedSeats);
            log.info("Scheduled task completed successfully. Cancelled {} bookings and released {} seats", 
                cancelledBookings, releasedSeats);
                
        } catch (Exception e) {
            log.error("Error in scheduled task to cancel expired bookings: {}", e.getMessage(), e);
        }
    }
} 
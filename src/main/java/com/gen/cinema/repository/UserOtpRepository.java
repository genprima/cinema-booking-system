package com.gen.cinema.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.User;
import com.gen.cinema.domain.UserOtp;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {

    @Query("SELECT uo FROM UserOtp uo WHERE uo.user = :user AND uo.otp = :otp AND uo.expiresAt > :now AND uo.used = false")
    Optional<UserOtp> findValidOtp(@Param("user") User user, @Param("otp") String otp, @Param("now") Instant now);

    @Query("SELECT uo FROM UserOtp uo WHERE uo.user.id = :userId AND uo.expiresAt > :now AND uo.used = false ORDER BY uo.createdAt DESC")
    Optional<UserOtp> findLatestActiveOtp(@Param("userId") Long userId, @Param("now") Instant now);

    Optional<UserOtp> findFirstByUserAndUsedFalseOrderByCreatedAtDesc(User user);
} 
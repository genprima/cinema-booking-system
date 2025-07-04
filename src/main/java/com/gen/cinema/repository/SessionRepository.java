package com.gen.cinema.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gen.cinema.domain.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    
    Optional<Session> findBySessionId(String sessionId);
    
    Optional<Session> findBySessionIdAndUserEmail(String sessionId, String userEmail);
    
    List<Session> findByUserEmail(String userEmail);
    
    @Query("SELECT s FROM Session s WHERE s.expiryTime < :currentTime")
    List<Session> findExpiredSessions(@Param("currentTime") LocalDateTime currentTime);
    
    @Modifying
    @Query("DELETE FROM Session s WHERE s.expiryTime < :currentTime")
    int deleteExpiredSessions(@Param("currentTime") LocalDateTime currentTime);
    
    @Modifying
    @Query("UPDATE Session s SET s.lastAccessedTime = :currentTime WHERE s.sessionId = :sessionId")
    int updateLastAccessedTime(@Param("sessionId") String sessionId, @Param("currentTime") LocalDateTime currentTime);
} 
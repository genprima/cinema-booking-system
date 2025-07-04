package com.gen.cinema.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Table(name = "sessions", indexes = {
    @Index(name = "idx_sessions_session_id", columnList = "session_id"),
    @Index(name = "idx_sessions_expiry_time", columnList = "expiry_time")
})
public class Session extends AbstractBaseEntity {

    @Column(name = "session_id", nullable = false, unique = true)
    private String sessionId;

    @Column(name = "user_email")
    private String userEmail;

    @Lob
    @Column(name = "session_data")
    private String sessionData;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "last_accessed_time", nullable = false)
    private LocalDateTime lastAccessedTime;

    public Session() {
    }

    public Session(String sessionId, String userEmail, String sessionData, LocalDateTime expiryTime) {
        this.sessionId = sessionId;
        this.userEmail = userEmail;
        this.sessionData = sessionData;
        this.expiryTime = expiryTime;
        this.lastAccessedTime = LocalDateTime.now();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSessionData() {
        return sessionData;
    }

    public void setSessionData(String sessionData) {
        this.sessionData = sessionData;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public LocalDateTime getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(LocalDateTime lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }
} 
package com.gen.cinema.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gen.cinema.domain.Session;
import com.gen.cinema.repository.SessionRepository;
import com.gen.cinema.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {
    
    private static final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);
    
    private final SessionRepository sessionRepository;
    
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    
    @Override
    @Transactional
    public String createSession(String userEmail, String sessionData, int expiryMinutes) {
        String sessionId = UUID.randomUUID().toString();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(expiryMinutes);
        
        Session session = new Session(sessionId, userEmail, sessionData, expiryTime);
        sessionRepository.save(session);
        
        log.debug("Created session: {} for user: {} with expiry: {}", sessionId, userEmail, expiryTime);
        return sessionId;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<String> getSessionData(String sessionId) {
        log.debug("Retrieving session data for sessionId: {}", sessionId);
        
        if (sessionId == null || sessionId.trim().isEmpty()) {
            log.warn("Session ID is null or empty");
            return Optional.empty();
        }
        
        Optional<Session> sessionOpt = sessionRepository.findBySessionId(sessionId);
        
        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            log.debug("Found session: {} for user: {}", sessionId, session.getUserEmail());
            
            if (session.isExpired()) {
                log.warn("Session expired: {} for user: {}", sessionId, session.getUserEmail());
                sessionRepository.delete(session);
                return Optional.empty();
            }
            
            log.debug("Session validated successfully: {} for user: {}", sessionId, session.getUserEmail());
            return Optional.of(session.getSessionData());
        } else {
            log.warn("Session not found: {}", sessionId);
            return Optional.empty();
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<String> getSessionData(String sessionId, String userEmail) {
        Optional<Session> sessionOpt = sessionRepository.findBySessionIdAndUserEmail(sessionId, userEmail);
        
        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            
            if (session.isExpired()) {
                log.debug("Session expired: {} for user: {}", sessionId, userEmail);
                sessionRepository.delete(session);
                return Optional.empty();
            }
            
            return Optional.of(session.getSessionData());
        }
        
        return Optional.empty();
    }
    
    @Override
    @Transactional
    public boolean updateSessionData(String sessionId, String sessionData) {
        Optional<Session> sessionOpt = sessionRepository.findBySessionId(sessionId);
        
        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            
            if (session.isExpired()) {
                log.debug("Cannot update expired session: {}", sessionId);
                sessionRepository.delete(session);
                return false;
            }
            
            session.setSessionData(sessionData);
            session.setLastAccessedTime(LocalDateTime.now());
            sessionRepository.save(session);
            
            log.debug("Updated session data: {}", sessionId);
            return true;
        }
        
        return false;
    }
    
    @Override
    @Transactional
    public boolean deleteSession(String sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findBySessionId(sessionId);
        
        if (sessionOpt.isPresent()) {
            sessionRepository.delete(sessionOpt.get());
            log.debug("Deleted session: {}", sessionId);
            return true;
        }
        
        return false;
    }
    
    @Override
    @Transactional
    public void deleteUserSessions(String userEmail) {
        sessionRepository.findByUserEmail(userEmail)
            .forEach(session -> sessionRepository.delete(session));
        
        log.debug("Deleted all sessions for user: {}", userEmail);
    }
    
    @Override
    @Transactional
    public int cleanupExpiredSessions() {
        LocalDateTime currentTime = LocalDateTime.now();
        int deletedCount = sessionRepository.deleteExpiredSessions(currentTime);
        
        if (deletedCount > 0) {
            log.info("Cleaned up {} expired sessions", deletedCount);
        }
        
        return deletedCount;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isValidSession(String sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findBySessionId(sessionId);
        
        if (sessionOpt.isPresent()) {
            Session session = sessionOpt.get();
            
            if (session.isExpired()) {
                log.debug("Session expired: {}", sessionId);
                sessionRepository.delete(session);
                return false;
            }
            
            return true;
        }
        
        return false;
    }
    
    @Override
    @Transactional
    public void updateLastAccessedTime(String sessionId) {
        if (sessionId != null && !sessionId.trim().isEmpty()) {
            sessionRepository.updateLastAccessedTime(sessionId, LocalDateTime.now());
            log.debug("Updated last accessed time for session: {}", sessionId);
        }
    }
    
    /**
     * Scheduled task to clean up expired sessions every hour
     */
    @Scheduled(fixedRate = 3600000) // Every hour
    @Transactional
    public void scheduledCleanup() {
        cleanupExpiredSessions();
    }
} 
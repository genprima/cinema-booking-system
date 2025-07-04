package com.gen.cinema.service;

import java.util.Optional;

public interface SessionService {
    
    /**
     * Create a new session for a user
     */
    String createSession(String userEmail, String sessionData, int expiryMinutes);
    
    /**
     * Get session data by session ID
     */
    Optional<String> getSessionData(String sessionId);
    
    /**
     * Get session data by session ID and user email (for security)
     */
    Optional<String> getSessionData(String sessionId, String userEmail);
    
    /**
     * Update session data
     */
    boolean updateSessionData(String sessionId, String sessionData);
    
    /**
     * Delete a session
     */
    boolean deleteSession(String sessionId);
    
    /**
     * Delete all sessions for a user
     */
    void deleteUserSessions(String userEmail);
    
    /**
     * Clean up expired sessions
     */
    int cleanupExpiredSessions();
    
    /**
     * Check if session exists and is valid
     */
    boolean isValidSession(String sessionId);
    
    /**
     * Update the last accessed time for a session
     */
    void updateLastAccessedTime(String sessionId);
} 
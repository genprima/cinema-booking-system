package com.gen.cinema.exception;
import org.springframework.http.HttpStatus;

public class BadRequestAlertException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public BadRequestAlertException(String message) {
        this(HttpStatus.BAD_REQUEST, "BAD_REQUEST", message);
    }

    public BadRequestAlertException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
} 
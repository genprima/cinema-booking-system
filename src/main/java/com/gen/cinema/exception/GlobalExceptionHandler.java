package com.gen.cinema.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.gen.cinema.dto.ErrorResponseDTO;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadRequestAlertException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestAlertException(BadRequestAlertException ex) {
        log.debug("Handling BadRequestAlertException: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.debug("Handling MethodArgumentNotValidException: {}", ex.getMessage());
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        
        ErrorResponseDTO error = new ErrorResponseDTO("VALIDATION_ERROR", message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        log.debug("Handling ConstraintViolationException: {}", ex.getMessage());
        String message = ex.getConstraintViolations().stream()
                .map(violation -> {
                    String propertyPath = violation.getPropertyPath().toString();
                    String field = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
                    return field + ": " + violation.getMessage();
                })
                .findFirst()
                .orElse("Validation failed");
        
        ErrorResponseDTO error = new ErrorResponseDTO("VALIDATION_ERROR", message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.debug("Handling MethodArgumentTypeMismatchException: {}", ex.getMessage());
        String message = ex.getName() + ": Invalid value '" + ex.getValue() + "'";
        ErrorResponseDTO error = new ErrorResponseDTO("VALIDATION_ERROR", message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingPathVariable(MissingPathVariableException ex) {
        log.debug("Handling MissingPathVariableException: {}", ex.getMessage());
        String message = ex.getVariableName() + ": Path variable is required";
        ErrorResponseDTO error = new ErrorResponseDTO("VALIDATION_ERROR", message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleNoHandlerFoundException(Exception ex) {
        log.debug("Handling NoHandlerFoundException/NoResourceFoundException: {}", ex.getMessage());
        String message;
        if (ex instanceof NoHandlerFoundException) {
            NoHandlerFoundException nhfe = (NoHandlerFoundException) ex;
            String url = nhfe.getRequestURL();
            if (url.endsWith("/")) {
                message = "Missing path variable in URL. Please provide all required path variables.";
            } else {
                message = "No mapping found for " + nhfe.getHttpMethod() + " " + url;
            }
        } else {
            NoResourceFoundException nrfe = (NoResourceFoundException) ex;
            String url = nrfe.getResourcePath();
            if (url.endsWith("/")) {
                message = "Missing path variable in URL. Please provide all required path variables.";
            } else {
                message = "No mapping found for " + nrfe.getHttpMethod() + " " + url;
            }
        }
        ErrorResponseDTO error = new ErrorResponseDTO("NOT_FOUND", message);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ErrorResponseDTO error = new ErrorResponseDTO("INTERNAL_SERVER_ERROR", "An unexpected error occurred");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 
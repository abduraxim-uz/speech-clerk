package uz.abduraxim.speechclerk.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.InsufficientResourcesException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> DataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> UsernameNotFoundException(UsernameNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }

    @ExceptionHandler(InsufficientResourcesException.class)
    public ResponseEntity<ErrorResponse> InsufficientResourcesException(InsufficientResourcesException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.PAYMENT_REQUIRED, ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
    }
}

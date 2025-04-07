package com.odp.walled.exception;

import com.odp.walled.dto.common.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler({DuplicateException.class, InsufficientBalanceException.class, PinException.class})
    public ResponseEntity<ApiResponse<Void>> handleBadRequests(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors));
    }

    // ✅ Menangani validasi di level service/repo (misalnya pakai Validator manual)
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getConstraintViolations().forEach(violation -> {
//            String field = violation.getPropertyPath().toString();
//            errors.put(field, violation.getMessage());
//        });
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors));
//    }

    // ✅ Global handler fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception exception) {
        exception.printStackTrace(); // TODO: Integrate with observability/logging system

        if (exception instanceof BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "The email or password is incorrect"));
        }

        if (exception instanceof AccountStatusException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(HttpStatus.FORBIDDEN.value(), "The account is locked"));
        }

        if (exception instanceof AccessDeniedException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(HttpStatus.FORBIDDEN.value(), "You are not authorized to access this resource"));
        }

        if (exception instanceof SignatureException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(HttpStatus.FORBIDDEN.value(), "The JWT signature is invalid"));
        }

        if (exception instanceof ExpiredJwtException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(HttpStatus.FORBIDDEN.value(), "The JWT token has expired"));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown internal server error."));
    }
}

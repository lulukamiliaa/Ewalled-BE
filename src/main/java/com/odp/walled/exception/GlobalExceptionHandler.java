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

/**
 * Global handler for application-wide exceptions.
 * Converts exceptions into standardized API responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions when a requested resource is not found.
     *
     * @param ex the thrown {@link ResourceNotFound}
     * @return standardized 404 response
     */
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    /**
     * Handles common business logic exceptions.
     *
     * @param ex the thrown {@link RuntimeException} (e.g., duplicate, balance, or PIN errors)
     * @return standardized 400 response
     */
    @ExceptionHandler({DuplicateException.class, InsufficientBalanceException.class, PinException.class})
    public ResponseEntity<ApiResponse<Void>> handleBadRequests(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    /**
     * Handles field-level validation failures (e.g., @NotBlank, @Email).
     *
     * @param ex {@link MethodArgumentNotValidException} from validation framework
     * @return response with detailed field error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors));
    }

    /**
     * Handles the case when a wallet already exists for the user.
     *
     * @param ex the thrown {@link WalletAlreadyExistsException}
     * @return 400 response with specific error message
     */
    @ExceptionHandler(WalletAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleWalletExists(WalletAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    /**
     * Catches any uncaught or generic exceptions and maps known auth-related ones to proper responses.
     *
     * @param exception the caught {@link Exception}
     * @return mapped HTTP response with relevant message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception exception) {
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

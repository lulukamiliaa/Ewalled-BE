package com.odp.walled.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler({DuplicateException.class, InsufficientBalanceException.class})
    public ResponseEntity<ErrorResponse> handleBadRequests(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception) {
        ErrorResponse errorDetail = new ErrorResponse(0, null);
        
        // TODO: send this stack trace to an observability tool
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail.setStatus(HttpStatus.UNAUTHORIZED.value());
            errorDetail.setMessage("The email or password is incorrect");
            return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
        }

        if (exception instanceof AccountStatusException) {
            errorDetail.setStatus(HttpStatus.FORBIDDEN.value());
            errorDetail.setMessage("The account is locked");
            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail.setStatus(HttpStatus.FORBIDDEN.value());
            errorDetail.setMessage("You are not authorized to access this resource");
            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof SignatureException) {
            errorDetail.setStatus(HttpStatus.FORBIDDEN.value());
            errorDetail.setMessage("The JWT signature is invalid");
            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail.setStatus(HttpStatus.FORBIDDEN.value());
            errorDetail.setMessage("The JWT token has expired");
            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        // For all other exceptions, return a generic internal server error
        errorDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetail.setMessage("Unknown internal server error.");
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
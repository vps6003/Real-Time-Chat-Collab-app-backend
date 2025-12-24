
package com.chatapp.app_backend.exception;

import com.chatapp.app_backend.dto.error.ErrorResponse;
import com.chatapp.app_backend.exception.BadRequestException;
import com.chatapp.app_backend.exception.BaseException;
import com.chatapp.app_backend.exception.ForbiddenException;
import com.chatapp.app_backend.exception.NotFoundException;
import com.chatapp.app_backend.exception.UnauthorizedException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ===============================
       Forbidden  exception fallback
       =============================== */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(
            ForbiddenException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorResponse(
                        ex.getStatus(),
                        ex.getError(),
                        ex.getErrorCode(),
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }
        /* ===============================
           400 – Bad Request
           =============================== */
        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequest(
                BadRequestException ex,
                HttpServletRequest request
        ) {

            return ResponseEntity
                    .status(ex.getStatus())
                    .body(new ErrorResponse(
                            ex.getStatus(),
                            ex.getError(),
                            ex.getErrorCode(),
                            ex.getMessage(),
                            request.getRequestURI()
                    ));
        }

    /* ===============================
       401 – Unauthorized
       =============================== */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorResponse(
                        ex.getStatus(),
                        ex.getError(),
                        ex.getErrorCode(),
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    /* ===============================
       404 – Not Found
       =============================== */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorResponse(
                        ex.getStatus(),
                        ex.getError(),
                        ex.getErrorCode(),
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    /* ===============================
       Base domain exception fallback
       =============================== */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BaseException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorResponse(
                        ex.getStatus(),
                        ex.getError(),
                        ex.getErrorCode(),
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }



    /* ===============================
       500 – Internal Server Error
       =============================== */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnhandled(
            Exception ex,
            HttpServletRequest request
    ) {
        return response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong",
                request
        );
    }

    /* ===============================
       Shared response builder
       =============================== */
    private ResponseEntity<ErrorResponse> response(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                "500",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

}

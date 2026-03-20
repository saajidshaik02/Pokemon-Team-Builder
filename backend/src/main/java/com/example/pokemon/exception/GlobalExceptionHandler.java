package com.example.pokemon.exception;

import com.example.pokemon.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Global REST exception handler for the backend API.
 *
 * <p>This advice converts application exceptions and common request errors into a
 * consistent JSON error payload so every endpoint responds in the same format.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles invalid Pokemon lookups.
     *
     * @param exception application exception describing the lookup failure
     * @param request current HTTP request
     * @return a {@code 404 Not Found} error payload
     */
    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePokemonNotFound(PokemonNotFoundException exception, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI());
    }

    /**
     * Handles validation errors triggered by service-layer argument checks.
     *
     * @param exception validation exception
     * @param request current HTTP request
     * @return a {@code 400 Bad Request} error payload
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getRequestURI());
    }

    /**
     * Handles upstream service failures such as PokeAPI outages.
     *
     * @param exception upstream service exception
     * @param request current HTTP request
     * @return a {@code 502 Bad Gateway} error payload
     */
    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleExternalService(ExternalServiceException exception, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_GATEWAY, exception.getMessage(), request.getRequestURI());
    }

    /**
     * Handles malformed JSON request bodies.
     *
     * @param exception Spring exception triggered during request parsing
     * @param request current HTTP request
     * @return a {@code 400 Bad Request} error payload
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Request body must be valid JSON.", request.getRequestURI());
    }

    /**
     * Handles unexpected uncaught exceptions.
     *
     * @param exception unexpected application exception
     * @param request current HTTP request
     * @return a safe generic {@code 500 Internal Server Error} payload
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error. Please try again later.", request.getRequestURI());
    }

    /**
     * Creates the consistent JSON error payload used across the API.
     *
     * @param status HTTP status to return
     * @param message client-facing error message
     * @param path request path that triggered the error
     * @return HTTP response entity containing the normalized error payload
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, String path) {
        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                Instant.now()
        );
        return ResponseEntity.status(status).body(response);
    }
}

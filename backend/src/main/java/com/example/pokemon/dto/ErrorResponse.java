package com.example.pokemon.dto;

import java.time.Instant;

/**
 * Standard error payload returned by the API.
 *
 * @param status HTTP status code
 * @param error HTTP reason phrase
 * @param message client-facing error message
 * @param path request path that produced the error
 * @param timestamp timestamp at which the error response was created
 */
public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp
) {
}

package com.example.pokemon.dto;

/**
 * Response DTO for the health-check endpoint.
 *
 * @param status health status value returned by the API
 */
public record HealthStatusResponse(String status) {
}

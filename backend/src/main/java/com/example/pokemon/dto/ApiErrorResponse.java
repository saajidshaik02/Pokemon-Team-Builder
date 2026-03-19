package com.example.pokemon.dto;

public record ApiErrorResponse(
        int status,
        String error,
        String message
) {
}

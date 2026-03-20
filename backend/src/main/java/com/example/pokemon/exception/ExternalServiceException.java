package com.example.pokemon.exception;

/**
 * Exception used when an external dependency cannot provide the required data.
 *
 * <p>In this project it is primarily used for PokeAPI connectivity failures,
 * unusable upstream responses, or unexpected upstream HTTP errors.</p>
 */
public class ExternalServiceException extends RuntimeException {

    /**
     * Creates an external service exception with a client-facing message.
     *
     * @param message error message describing the upstream failure
     */
    public ExternalServiceException(String message) {
        super(message);
    }

    /**
     * Creates an external service exception with a client-facing message and cause.
     *
     * @param message error message describing the upstream failure
     * @param cause original exception that triggered this failure
     */
    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

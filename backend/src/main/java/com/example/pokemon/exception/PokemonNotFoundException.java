package com.example.pokemon.exception;

/**
 * Exception raised when a requested Pokemon cannot be found.
 *
 * <p>This exception is used to produce a clear {@code 404 Not Found} response
 * for invalid Pokemon names.</p>
 */
public class PokemonNotFoundException extends RuntimeException {

    /**
     * Creates a not-found exception for the supplied Pokemon name.
     *
     * @param name normalized Pokemon name that could not be resolved
     */
    public PokemonNotFoundException(String name) {
        super("Pokemon '" + name + "' was not found. Check the name and try again.");
    }
}

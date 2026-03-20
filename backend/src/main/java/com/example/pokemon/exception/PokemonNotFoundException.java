package com.example.pokemon.exception;

public class PokemonNotFoundException extends RuntimeException {

    public PokemonNotFoundException(String name) {
        super("Pokemon '" + name + "' was not found. Check the name and try again.");
    }
}

package com.example.pokemon.controller;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.service.PokemonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for single-Pokemon lookup operations.
 *
 * <p>This controller exposes {@code GET /api/pokemon/{name}} and returns normalized
 * Pokemon data rather than the raw upstream PokeAPI response.</p>
 */
@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    /**
     * Creates a new Pokemon lookup controller.
     *
     * @param pokemonService service responsible for Pokemon lookup orchestration
     */
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    /**
     * Fetches a Pokemon by name.
     *
     * @param name Pokemon name received from the request path
     * @return normalized Pokemon details for the requested Pokemon
     * @throws IllegalArgumentException if the supplied name is blank
     * @throws com.example.pokemon.exception.PokemonNotFoundException if the Pokemon is not found
     * @throws com.example.pokemon.exception.ExternalServiceException if PokeAPI cannot be reached
     */
    @GetMapping("/{name}")
    public PokemonDetailsResponse getPokemonByName(@PathVariable String name) {
        return pokemonService.getPokemonByName(name);
    }
}

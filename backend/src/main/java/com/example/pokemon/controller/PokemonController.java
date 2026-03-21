package com.example.pokemon.controller;

import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pokemon", description = "Single-Pokemon lookup endpoints")
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
    @Operation(
            summary = "Look up a Pokemon by name",
            description = "Returns normalized Pokemon data from the backend DTO contract rather than the raw PokeAPI payload."
    )
    @ApiResponse(responseCode = "200", description = "Pokemon found")
    @ApiResponse(responseCode = "400", description = "Pokemon name was blank")
    @ApiResponse(responseCode = "404", description = "Pokemon was not found")
    @ApiResponse(responseCode = "502", description = "PokeAPI could not be reached")
    public PokemonDetailsResponse getPokemonByName(
            @Parameter(description = "Pokemon name. Mixed case and surrounding whitespace are normalized by the backend.")
            @PathVariable String name
    ) {
        return pokemonService.getPokemonByName(name);
    }
}

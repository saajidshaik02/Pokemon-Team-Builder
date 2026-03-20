package com.example.pokemon.service;

import com.example.pokemon.client.PokeApiClient;
import com.example.pokemon.client.PokeApiPokemonResponse;
import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import com.example.pokemon.exception.PokemonNotFoundException;
import com.example.pokemon.mapper.PokemonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private PokeApiClient pokeApiClient;

    @Mock
    private PokemonMapper pokemonMapper;

    @InjectMocks
    private PokemonService pokemonService;

    @Test
    void shouldNormalizePokemonNameBeforeLookup() {
        PokeApiPokemonResponse clientResponse = new PokeApiPokemonResponse(
                25,
                "pikachu",
                List.of(),
                List.of(),
                List.of(),
                new PokeApiPokemonResponse.Sprites(
                        "https://example.test/pikachu.png",
                        new PokeApiPokemonResponse.OtherSprites(
                                new PokeApiPokemonResponse.OfficialArtwork("https://example.test/pikachu-artwork.png")
                        )
                )
        );
        PokemonDetailsResponse pokemonResponse = new PokemonDetailsResponse(
                25,
                "pikachu",
                List.of("electric"),
                List.of("static"),
                new PokemonStatsResponse(35, 55, 40, 50, 50, 90),
                "https://example.test/pikachu-artwork.png",
                "https://example.test/pikachu.png"
        );

        given(pokeApiClient.getPokemonByName("pikachu")).willReturn(clientResponse);
        given(pokemonMapper.toPokemonResponse(clientResponse)).willReturn(pokemonResponse);

        PokemonDetailsResponse result = pokemonService.getPokemonByName("  Pikachu  ");

        verify(pokeApiClient).getPokemonByName("pikachu");
        assertEquals("pikachu", result.name());
    }

    @Test
    void shouldRejectBlankPokemonName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pokemonService.getPokemonByName("   ")
        );

        assertEquals(PokemonService.BLANK_POKEMON_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldRejectNullPokemonName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pokemonService.getPokemonByName(null)
        );

        assertEquals(PokemonService.BLANK_POKEMON_NAME_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldPropagatePokemonNotFound() {
        given(pokeApiClient.getPokemonByName("missingno"))
                .willThrow(new PokemonNotFoundException("missingno"));

        PokemonNotFoundException exception = assertThrows(
                PokemonNotFoundException.class,
                () -> pokemonService.getPokemonByName("missingno")
        );

        assertEquals("Pokemon 'missingno' was not found. Check the name and try again.", exception.getMessage());
    }
}

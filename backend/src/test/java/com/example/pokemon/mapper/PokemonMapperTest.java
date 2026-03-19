package com.example.pokemon.mapper;

import com.example.pokemon.client.PokeApiPokemonResponse;
import com.example.pokemon.dto.PokemonResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokemonMapperTest {

    private final PokemonMapper pokemonMapper = new PokemonMapper();

    @Test
    void shouldMapPokeApiResponseToPokemonResponse() {
        PokeApiPokemonResponse response = new PokeApiPokemonResponse(
                6,
                "charizard",
                List.of(
                        new PokeApiPokemonResponse.TypeSlot(2, new PokeApiPokemonResponse.NamedApiResource("flying")),
                        new PokeApiPokemonResponse.TypeSlot(1, new PokeApiPokemonResponse.NamedApiResource("fire"))
                ),
                List.of(
                        new PokeApiPokemonResponse.AbilitySlot(new PokeApiPokemonResponse.NamedApiResource("blaze")),
                        new PokeApiPokemonResponse.AbilitySlot(new PokeApiPokemonResponse.NamedApiResource("solar-power"))
                ),
                List.of(
                        new PokeApiPokemonResponse.StatSlot(78, new PokeApiPokemonResponse.NamedApiResource("hp")),
                        new PokeApiPokemonResponse.StatSlot(84, new PokeApiPokemonResponse.NamedApiResource("attack")),
                        new PokeApiPokemonResponse.StatSlot(78, new PokeApiPokemonResponse.NamedApiResource("defense")),
                        new PokeApiPokemonResponse.StatSlot(109, new PokeApiPokemonResponse.NamedApiResource("special-attack")),
                        new PokeApiPokemonResponse.StatSlot(85, new PokeApiPokemonResponse.NamedApiResource("special-defense")),
                        new PokeApiPokemonResponse.StatSlot(100, new PokeApiPokemonResponse.NamedApiResource("speed"))
                ),
                new PokeApiPokemonResponse.Sprites("https://example.test/charizard.png")
        );

        PokemonResponse mapped = pokemonMapper.toPokemonResponse(response);

        assertEquals(6, mapped.id());
        assertEquals("charizard", mapped.name());
        assertEquals(List.of("fire", "flying"), mapped.types());
        assertEquals(List.of("blaze", "solar-power"), mapped.abilities());
        assertEquals(78, mapped.stats().hp());
        assertEquals(109, mapped.stats().specialAttack());
        assertEquals("https://example.test/charizard.png", mapped.spriteUrl());
    }
}

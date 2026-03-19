package com.example.pokemon.controller;

import com.example.pokemon.dto.PokemonResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import com.example.pokemon.exception.GlobalExceptionHandler;
import com.example.pokemon.exception.PokemonNotFoundException;
import com.example.pokemon.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PokemonController.class)
@Import(GlobalExceptionHandler.class)
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @Test
    void shouldReturnNormalizedPokemonResponse() throws Exception {
        PokemonResponse pokemonResponse = new PokemonResponse(
                25,
                "pikachu",
                List.of("electric"),
                List.of("static", "lightning-rod"),
                new PokemonStatsResponse(35, 55, 40, 50, 50, 90),
                "https://example.test/pikachu.png"
        );

        given(pokemonService.getPokemonByName("Pikachu")).willReturn(pokemonResponse);

        mockMvc.perform(get("/api/pokemon/Pikachu"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(25))
                .andExpect(jsonPath("$.name").value("pikachu"))
                .andExpect(jsonPath("$.types[0]").value("electric"))
                .andExpect(jsonPath("$.abilities[0]").value("static"))
                .andExpect(jsonPath("$.stats.specialAttack").value(50))
                .andExpect(jsonPath("$.spriteUrl").value("https://example.test/pikachu.png"));
    }

    @Test
    void shouldReturnNotFoundForInvalidPokemonName() throws Exception {
        given(pokemonService.getPokemonByName("missingno"))
                .willThrow(new PokemonNotFoundException("missingno"));

        mockMvc.perform(get("/api/pokemon/missingno"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Pokemon 'missingno' was not found."));
    }
}

package com.example.pokemon.mapper;

import com.example.pokemon.client.PokeApiPokemonResponse;
import com.example.pokemon.dto.PokemonDetailsResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Mapper that converts raw PokeAPI payloads into normalized API DTOs.
 *
 * <p>This class keeps transformation logic out of controllers and services so the
 * external payload shape remains isolated from the public API contract.</p>
 */
@Component
public class PokemonMapper {

    /**
     * Maps a raw PokeAPI Pokemon response into the normalized response DTO.
     *
     * @param response raw upstream Pokemon payload
     * @return normalized Pokemon details used by the API
     */
    public PokemonDetailsResponse toPokemonResponse(PokeApiPokemonResponse response) {
        return new PokemonDetailsResponse(
                response.id(),
                response.name(),
                mapTypes(response.types()),
                mapAbilities(response.abilities()),
                mapStats(response.stats()),
                extractOfficialArtworkUrl(response),
                response.sprites() != null ? response.sprites().front_default() : null
        );
    }

    /**
     * Extracts the official artwork URL from the nested upstream sprite payload.
     *
     * @param response raw upstream Pokemon payload
     * @return official artwork URL when present, otherwise {@code null}
     */
    private String extractOfficialArtworkUrl(PokeApiPokemonResponse response) {
        if (response.sprites() == null || response.sprites().other() == null) {
            return null;
        }

        PokeApiPokemonResponse.OfficialArtwork officialArtwork = response.sprites().other().official_artwork();
        return officialArtwork != null ? officialArtwork.front_default() : null;
    }

    /**
     * Extracts normalized type names from the raw upstream type slots.
     *
     * @param types raw upstream type-slot collection
     * @return ordered list of type names
     */
    private List<String> mapTypes(List<PokeApiPokemonResponse.TypeSlot> types) {
        if (types == null) {
            return List.of();
        }

        return types.stream()
                .sorted((left, right) -> Integer.compare(left.slot(), right.slot()))
                .map(PokeApiPokemonResponse.TypeSlot::type)
                .filter(type -> type != null && type.name() != null)
                .map(PokeApiPokemonResponse.NamedApiResource::name)
                .toList();
    }

    /**
     * Extracts normalized ability names from the raw upstream ability list.
     *
     * @param abilities raw upstream ability collection
     * @return list of ability names
     */
    private List<String> mapAbilities(List<PokeApiPokemonResponse.AbilitySlot> abilities) {
        if (abilities == null) {
            return List.of();
        }

        return abilities.stream()
                .map(PokeApiPokemonResponse.AbilitySlot::ability)
                .filter(ability -> ability != null && ability.name() != null)
                .map(PokeApiPokemonResponse.NamedApiResource::name)
                .toList();
    }

    /**
     * Maps upstream stat entries into the normalized stats DTO.
     *
     * @param stats raw upstream stat collection
     * @return normalized Pokemon stats DTO
     */
    private PokemonStatsResponse mapStats(List<PokeApiPokemonResponse.StatSlot> stats) {
        Map<String, Integer> statValues = new LinkedHashMap<>();

        if (stats != null) {
            for (PokeApiPokemonResponse.StatSlot stat : stats) {
                if (stat.stat() != null && stat.stat().name() != null) {
                    statValues.put(stat.stat().name(), stat.base_stat());
                }
            }
        }

        return new PokemonStatsResponse(
                statValues.getOrDefault("hp", 0),
                statValues.getOrDefault("attack", 0),
                statValues.getOrDefault("defense", 0),
                statValues.getOrDefault("special-attack", 0),
                statValues.getOrDefault("special-defense", 0),
                statValues.getOrDefault("speed", 0)
        );
    }
}

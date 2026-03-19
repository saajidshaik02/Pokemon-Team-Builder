package com.example.pokemon.mapper;

import com.example.pokemon.client.PokeApiPokemonResponse;
import com.example.pokemon.dto.PokemonResponse;
import com.example.pokemon.dto.PokemonStatsResponse;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class PokemonMapper {

    public PokemonResponse toPokemonResponse(PokeApiPokemonResponse response) {
        return new PokemonResponse(
                response.id(),
                response.name(),
                mapTypes(response.types()),
                mapAbilities(response.abilities()),
                mapStats(response.stats()),
                response.sprites() != null ? response.sprites().front_default() : null
        );
    }

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

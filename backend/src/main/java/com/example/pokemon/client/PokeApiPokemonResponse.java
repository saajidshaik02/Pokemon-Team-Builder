package com.example.pokemon.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Raw Pokemon payload returned by PokeAPI.
 *
 * <p>This record and its nested records intentionally mirror only the upstream fields
 * required by the application. Controllers never expose this raw payload directly.</p>
 *
 * @param id upstream Pokemon id
 * @param name upstream Pokemon name
 * @param types upstream type slot collection
 * @param abilities upstream ability collection
 * @param stats upstream base stat collection
 * @param sprites upstream sprite information
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PokeApiPokemonResponse(
        int id,
        String name,
        List<TypeSlot> types,
        List<AbilitySlot> abilities,
        List<StatSlot> stats,
        Sprites sprites
) {

    /**
     * Raw PokeAPI type-slot entry.
     *
     * @param slot slot order of the Pokemon type
     * @param type referenced type resource
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TypeSlot(int slot, NamedApiResource type) {
    }

    /**
     * Raw PokeAPI ability entry.
     *
     * @param ability referenced ability resource
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AbilitySlot(NamedApiResource ability) {
    }

    /**
     * Raw PokeAPI base-stat entry.
     *
     * @param base_stat numeric base stat value
     * @param stat referenced stat resource
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StatSlot(int base_stat, NamedApiResource stat) {
    }

    /**
     * Raw PokeAPI sprite payload.
     *
     * @param front_default default front sprite URL
     * @param other nested alternate-art payloads
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Sprites(String front_default, OtherSprites other) {
    }

    /**
     * Nested PokeAPI sprite collections.
     *
     * @param official_artwork official artwork payload
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OtherSprites(OfficialArtwork official_artwork) {
    }

    /**
     * Official artwork sprite payload.
     *
     * @param front_default official artwork URL
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OfficialArtwork(String front_default) {
    }

    /**
     * Generic named PokeAPI resource reference.
     *
     * @param name upstream resource name
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NamedApiResource(String name) {
    }
}

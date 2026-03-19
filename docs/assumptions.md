# Assumptions

## Product assumptions
- This is a backend-only application for version 1.
- Users will interact with the system through API requests rather than a frontend UI.
- The goal is to provide useful Pokémon team analysis, not full competitive battle simulation.

## Data assumptions
- PokéAPI is the single external data source.
- Pokémon names provided by the user will be normalized to lowercase before lookup.
- Only the required Pokémon fields will be used: id, name, types, abilities, base stats, and sprite.
- If PokéAPI is temporarily unavailable, the application should return a clear error instead of failing silently.

## Scope assumptions
- The first version will not support movesets, EVs, IVs, held items, natures, status effects, or battle mechanics.
- The first version will not support user accounts, persistence, or a database.
- Duplicate Pokémon in a team may be rejected in the first version for simplicity.
- Team size must be between 1 and 6 Pokémon.

## Analysis assumptions
- Type analysis will use simple type-effectiveness rules.
- Role classification will use base-stat heuristics only.
- Recommendations will be rule-based and deterministic.
- Recommendations do not need to be competitively optimal; they only need to be reasonable and explainable.

## Technical assumptions
- Java 21 and Spring Boot will be used.
- Maven will be used as the build tool.
- DTOs will be used for all API responses.
- Raw PokéAPI responses will not be exposed directly from controllers.
- The codebase should remain small, modular, and easy to explain.

## Validation assumptions
- Invalid Pokémon names should return a clear client-facing error.
- Empty team submissions should be rejected.
- Team submissions with more than 6 Pokémon should be rejected.
- Unexpected server failures should return a safe generic error response.

## Submission assumptions
- The project will be judged more on correctness, clarity, structure, and reasoning than on advanced mechanics.
- Simple and well-explained logic is preferred over over-engineered logic.
# Assumptions

## Product assumptions
- This is a backend-only application for version 1.
- Users interact with the system through API requests rather than a frontend UI.
- The goal is to provide useful Pokemon team analysis, not full competitive battle simulation.

## Data assumptions
- PokeAPI is the single external data source.
- Pokemon names provided by the user are normalized to lowercase before lookup.
- Only the required Pokemon fields are used: id, name, types, abilities, base stats, and sprite.
- If PokeAPI is temporarily unavailable, the application returns a clear client-facing error instead of failing silently.

## Scope assumptions
- The first version does not support movesets, EVs, IVs, held items, natures, status effects, or battle mechanics.
- The first version does not support user accounts, persistence, or a database.
- Duplicate Pokemon in a team are rejected in the first version to keep analysis output unambiguous.
- Team size must be between 1 and 6 Pokemon.

## Analysis assumptions
- Type analysis uses simple type-effectiveness rules.
- Role classification uses base-stat heuristics only.
- Stat summary uses simple average-based thresholds for speed, bulk, and offensive pressure.
- Recommendations are rule-based and deterministic.
- Recommendations do not need to be competitively optimal; they only need to be reasonable and explainable.

## Technical assumptions
- Java 21 and Spring Boot are used.
- Maven is used as the build tool.
- DTOs are used for all API responses.
- Raw PokeAPI responses are not exposed directly from controllers.
- The codebase should remain small, modular, and easy to explain.
- Client-facing error responses follow a consistent JSON structure with `status`, `error`, `message`, `path`, and `timestamp`.

## Validation assumptions
- Invalid Pokemon names return a clear `404` client-facing error.
- Empty team submissions are rejected.
- Team submissions with more than 6 Pokemon are rejected.
- Malformed JSON request bodies return a clear `400` error.
- Unexpected server failures return a safe generic error response.

## Submission assumptions
- The project is judged more on correctness, clarity, structure, and reasoning than on advanced mechanics.
- Simple and well-explained logic is preferred over over-engineered logic.
- Postman remains the recommended tool for manual endpoint checks documented in the README.

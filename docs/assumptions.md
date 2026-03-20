# Assumptions

## Product assumptions

- The project is now a small full-stack application rather than a backend-only submission.
- The Spring Boot backend is already implemented and remains the source of truth for Pokemon data and team analysis.
- The next major phase is a React frontend that consumes the backend REST API.
- The goal remains useful Pokemon team analysis, not full competitive battle simulation.

## Data assumptions

- PokeAPI is the only external data source.
- The frontend does not call PokeAPI directly.
- Pokemon names are normalized to lowercase by the backend before lookup.
- Only the required Pokemon fields are used: id, name, types, abilities, base stats, and sprite URL.
- If the frontend is expected to use official artwork through the backend only, the backend response contract must expose that artwork URL explicitly.
- Pokemon image handling prefers official artwork when available and falls back to classic sprite URLs when official artwork is missing.
- If PokeAPI is unavailable, the backend returns a clear client-facing error instead of failing silently.

## Scope assumptions

- The application supports single Pokemon lookup and team analysis for 1 to 6 Pokemon.
- Duplicate Pokemon are currently rejected to keep analysis output unambiguous.
- The current version does not support movesets, EVs, IVs, held items, natures, status effects, or battle simulation.
- The current version does not support user accounts, persistence, or a database.

## Frontend assumptions

- The frontend will be implemented with React under `frontend/`.
- Vite will be used to bootstrap the React app unless requirements change.
- React Router will be used for the main views: Pokedex, Team Builder, and Team Analysis.
- Axios will be used for frontend REST calls to the backend.
- The frontend should prefer backend-provided `officialArtworkUrl` when that value is available from fetched Pokemon data.
- The frontend should fall back to classic sprite URLs when official artwork is unavailable.
- The frontend should show loading placeholders or skeleton states while Pokemon images are loading.
- The frontend should handle broken or missing image URLs gracefully.
- The frontend will use simple local state and React hooks before introducing heavier state management.
- Client-side validation is used for better UX, but backend validation remains authoritative.
- The frontend should surface backend error messages directly when possible.
- The frontend should be responsive and mobile-friendly.

## UI assumptions

- Pokemon types are displayed as badge-style UI elements with type-specific colors.
- Pokemon stats are displayed as readable numeric rows with bars or progress-style indicators.
- The team builder always shows 6 visible team slots.
- Empty team slots show a plus-style action or empty placeholder state.
- Filled team slots should show official artwork when possible and sprite fallback otherwise.
- The team builder prevents the user from adding more than 6 Pokemon on the client.
- The analysis view prioritizes weaknesses and recommendations visually over lower-priority sections.
- The UI should provide visible feedback for image loading and backend error states.

## Analysis assumptions

- Type analysis uses simple type-effectiveness rules.
- Role classification uses base-stat heuristics only.
- Stat summary uses simple totals, averages, and threshold-based strength or weakness notes.
- Recommendations are deterministic and rule-based.
- Recommendations do not need to be competitively optimal; they need to be reasonable and explainable.

## Technical assumptions

- Java 21 and Spring Boot are used for the backend.
- Maven is used as the backend build tool.
- React is used for the frontend.
- DTOs are used for backend API responses.
- Raw PokeAPI responses are not exposed directly from controllers.
- The codebase should remain small, modular, and easy to explain.
- Client-facing backend errors follow a consistent JSON structure with `status`, `error`, `message`, `path`, and `timestamp`.

## Validation assumptions

- Invalid Pokemon names return a clear `404` backend error.
- Blank Pokemon names return a clear `400` backend error.
- Empty team submissions are rejected.
- Team submissions with more than 6 Pokemon are rejected.
- Frontend team-size validation should also enforce a minimum of 1 and a maximum of 6 Pokemon before submission.
- Duplicate Pokemon submissions are rejected in the current backend implementation.
- Malformed JSON request bodies return a clear `400` error.
- Unexpected server failures return a safe generic `500` error response.

## Development assumptions

- Backend local development runs from `backend/` with Maven.
- The repository currently does not include a Maven wrapper script, so backend commands use the installed `mvn` binary.
- Frontend local development will run from `frontend/` with Node.js and npm.
- Frontend backend URLs should be configured through environment variables instead of hardcoded values.
- Postman should still be used for direct backend endpoint testing even after the frontend exists.

## Submission assumptions

- The project is judged more on correctness, clarity, structure, and reasoning than on advanced mechanics.
- Simple and well-explained logic is preferred over over-engineered logic.
- A polished frontend should improve usability, but it should not introduce unnecessary architectural complexity.

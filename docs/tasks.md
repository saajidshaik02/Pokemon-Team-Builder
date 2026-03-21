# Tasks

## Current goal

Turn the existing Spring Boot Pokemon Team Analysis backend into a usable full-stack application by adding a React frontend without breaking the current API behavior.

## Current project state

Backend complete:
- [x] Spring Boot project scaffolded
- [x] Layered backend package structure in place
- [x] `GET /api/pokemon/{name}` implemented
- [x] `POST /api/team/analyze` implemented
- [x] Type analysis implemented
- [x] Role analysis implemented
- [x] Stat summary implemented
- [x] Recommendation generation implemented
- [x] Global error handling implemented
- [x] Backend tests added and passing in the project environment used during implementation

Documentation alignment:
- [x] Update repository rules to allow and define frontend work
- [x] Update `docs/architecture.md` for full-stack scope
- [x] Update `docs/assumptions.md` for frontend integration
- [x] Update `README.md` with current backend status and frontend integration plan
- [x] Update `docs/session-log.md` with the documentation review session

Backend maintenance:
- [x] Refactor role classification to use relative stat distribution heuristics instead of only flat raw-stat thresholds
- [x] Centralize backend analysis threshold constants to remove service-level magic numbers in the impacted analysis services
- [x] Add Swagger or OpenAPI support for manual backend API testing

## Phase 1: Frontend planning and setup

- [x] Create a React app under `frontend/`
- [x] Use Vite for frontend bootstrapping and document the choice
- [x] Add frontend folders for `api`, `components`, `pages`, `layouts`, `hooks`, and `styles`
- [x] Add React Router for the main frontend views
- [x] Add Axios for backend HTTP calls
- [x] Ensure the backend API contract exposes both `spriteUrl` and `officialArtworkUrl`
- [x] Add an environment-based backend base URL configuration
- [x] Add shared app shell layout with header, navigation, and responsive content container
- [x] Add global CSS variables for type colors, spacing, shadows, and breakpoints
- [x] Add reusable loading, empty-state, and error-state UI components
- [x] Add shared image utility that selects the correct image type for each view

## Phase 2: Pokedex view

- [x] Create `PokedexPage`
- [x] Create `PokemonSearchForm` with single-name input and submit action
- [x] Create `PokemonCard` that supports both sprite and official artwork rendering
- [x] Create `PokemonAbilitiesList`
- [x] Create `PokemonStatsPanel` with numeric values and progress bars
- [x] Create `TypeBadge` component with per-type styling
- [x] Add frontend validation for blank Pokemon names
- [x] Call `GET /api/pokemon/{name}` through the frontend API layer
- [x] Use `spriteUrl` for quick Pokemon search results and profile-style lookup previews
- [x] Use `officialArtworkUrl` for polished detailed Pokemon views when available
- [x] Fallback from `officialArtworkUrl` to `spriteUrl` when official artwork is missing
- [x] Add image loading skeleton or loading state for Pokemon images
- [x] Add image fallback handling for missing or broken artwork URLs
- [x] Show loading, success, empty, and invalid-name error states

Pokedex view breakdown:
- Components needed: `PokedexPage`, `PokemonSearchForm`, `PokemonCard`, `PokemonAbilitiesList`, `PokemonStatsPanel`, `TypeBadge`, `LoadingState`, `ErrorNotice`, `ImageLoader`
- Data flow: search form -> API helper -> `GET /api/pokemon/{name}` -> normalized response stored in page state -> search and quick profile states use `spriteUrl`, detailed display can switch to `officialArtworkUrl` with `spriteUrl` fallback -> detail components render from state
- UX display: quick lookup can feel lively with sprite usage, detailed card can use official artwork for a cleaner profile view, stat bars remain readable, backend error message stays near the search form
- Step-by-step sub-tasks:
- [x] Wire the page route and layout entry
- [x] Build the search form and controlled input state
- [x] Add the lookup API client method
- [x] Add image URL resolution logic for artwork and sprite fallback
- [x] Render image loading state before artwork resolves
- [x] Render fetched Pokemon details on success
- [x] Render backend error states cleanly on failure

## Phase 3: Team Builder view

- [x] Create `TeamBuilderPage`
- [x] Create `TeamSlotsGrid` with exactly 6 visible slots
- [x] Create `TeamSlotCard` for sprite-first team-slot rendering, with artwork support if needed later
- [x] Create `EmptyTeamSlot` with plus icon or add action
- [x] Create `AddPokemonForm` for adding a Pokemon to the team
- [x] Create `TeamValidationMessage` for duplicate, blank, and max-size errors
- [x] Add local state for team composition and slot ordering
- [x] Prevent adding more than 6 Pokemon
- [x] Prevent blank entries in the UI before submit
- [x] Use `spriteUrl` in team slots so the builder stays compact and visually active
- [x] Keep `officialArtworkUrl` available in team member state for later use in analysis or detailed views
- [x] Fallback cleanly if a slot sprite URL is missing or broken
- [x] Add slot-level image loading and broken-image fallback handling
- [x] Surface duplicate-name errors from the backend clearly

Team Builder breakdown:
- Components needed: `TeamBuilderPage`, `TeamSlotsGrid`, `TeamSlotCard`, `EmptyTeamSlot`, `AddPokemonForm`, `TypeBadge`, `TeamValidationMessage`, `ImageLoader`
- Data flow: add form -> local team state stores `name`, `spriteUrl`, and `officialArtworkUrl` when available -> slots grid renders `spriteUrl` first -> submit action passes `pokemonNames` array to analysis API
- UX display: fixed six-slot grid, empty slots visible at all times, animated or classic sprites keep the builder lively, clear disabled state when the team is full
- Step-by-step sub-tasks:
- [x] Build six-slot grid layout
- [x] Add team state and slot update helpers
- [x] Add add/remove Pokemon actions
- [x] Add inline validation for blank and over-limit cases
- [x] Add image resolution and fallback logic for team slots
- [x] Add summary state showing current team count
- [x] Keep the analysis action disabled until at least one Pokemon is present

Phase 3 note:
- Duplicate-team feedback is mirrored in the Team Builder UI using the backend rule text so users see the same constraint before Phase 4 wires the analysis endpoint.

## Phase 4: Team Analysis view

- [x] Create `TeamAnalysisPage`
- [x] Create `TeamAnalysisForm` or integrate submit action from Team Builder
- [x] Create `AnalysisSummaryPanel`
- [x] Create `TypeAnalysisSection` for weaknesses, resistances, immunities, and synergy notes
- [x] Create `RoleAnalysisSection` for roles, role counts, and summary
- [x] Create `StatSummarySection` for totals, averages, strengths, and weaknesses
- [x] Create `RecommendationsSection`
- [x] Create `WeaknessList`, `ResistanceList`, and `ImmunityList` subcomponents
- [x] Call `POST /api/team/analyze` through the frontend API layer
- [x] Add summary rendering for team member official artwork in the analysis header, with sprite fallback if artwork is missing
- [x] Show loading, success, empty, and backend validation failure states

Team Analysis breakdown:
- Components needed: `TeamAnalysisPage`, `AnalysisSummaryPanel`, `TypeAnalysisSection`, `RoleAnalysisSection`, `StatSummarySection`, `RecommendationsSection`, `WeaknessList`, `ResistanceList`, `ImmunityList`, `LoadingState`, `ErrorNotice`
- Data flow: team builder state or analysis form -> API helper -> `POST /api/team/analyze` -> analysis response stored in page state -> analysis header prefers cached `officialArtworkUrl`, falls back to cached `spriteUrl`, and each section reads only its slice of the response
- UX display: sectioned card layout, polished team presentation through official artwork, weaknesses shown first, role summary and recommendations easy to scan, empty recommendations state shown explicitly
- Step-by-step sub-tasks:
- [x] Add the analyze-team API client method
- [x] Define the frontend response model shape
- [x] Render high-level team summary
- [x] Render type analysis with severity emphasis for shared or major weaknesses
- [x] Render role and stat analysis in separate cards
- [x] Render recommendations as concise callouts

Phase 4 note:
- The Team Analysis route supports both direct manual team entry and route-state handoff from Team Builder. If cached artwork or sprite data is missing, the analysis page resolves summary images through backend Pokemon lookups rather than calling PokeAPI directly.

## Phase 5: Frontend state and backend integration

- [x] Add centralized API helper module for all backend requests
- [x] Normalize frontend error handling for `400`, `404`, `502`, and network failures
- [x] Keep the frontend image model aligned with the backend DTO fields for `officialArtworkUrl` and `spriteUrl`
- [x] Keep route-level state minimal and pass derived data into presentational components
- [x] Add optimistic UI only where it improves clarity without hiding backend validation
- [x] Add request cancellation or stale-response protection for repeated Pokemon searches
- [x] Add shared image resolver that can return sprite-first or artwork-first behavior depending on the view
- [x] Add shared image loading and image error state handling for Pokemon artwork
- [x] Add reusable hooks for loading and error state if duplication appears

Phase 5 note:
- Frontend backend calls should be imported through a shared `frontend/src/api/index.js` entry so page components stay decoupled from lower-level API module layout.
- Repeated Pokemon lookups should ignore stale earlier responses so the latest user search controls the visible state.
- Frontend DTO normalization should preserve the backend field names `officialArtworkUrl` and `spriteUrl` instead of re-mapping them into competing names.

## Phase 6: UI polish and responsiveness

- [x] Apply responsive layout rules for mobile, tablet, and desktop
- [x] Use type-colored badge styles for Pokemon types
- [x] Use sprites for Pokedex search previews and Team Builder slots
- [x] Use official artwork for detailed Pokemon cards and Team Analysis summaries
- [x] Add subtle card elevation, spacing, and section hierarchy for readability
- [x] Keep navigation and forms usable on small screens
- [x] Add empty-state messaging for no search result, no team members, and no recommendations
- [x] Add image loading placeholders or skeletons while artwork is loading

Design direction inspired by common Pokemon tools:
- [x] Borrow the clear searchable detail focus seen in Pokemon Database
- [x] Borrow the dense-but-readable team workflow from Pokemon Showdown's teambuilder
- [x] Borrow the structured stat and data grouping style common in Serebii Pokedex pages

Phase 6 note:
- Responsive polish should keep page-level layout hooks light: route pages may add modifier classes such as `page-grid--team-builder` or `page-grid--team-analysis`, while shared visual treatment stays centralized in `frontend/src/styles/globals.css`.
- Empty states should read like user guidance, not only raw error output, when the backend simply returns no useful result or when the user has not built enough local context yet.

## Phase 7: Frontend testing

- [x] Add tests for route rendering
- [x] Add tests for `PokemonSearchForm` behavior
- [x] Add tests for `AddPokemonForm` and team-slot interactions
- [x] Add tests for max-team validation and blank-entry validation
- [x] Add tests for Pokemon lookup API success and failure states
- [x] Add tests for team analysis API success and failure states
- [x] Add tests for empty recommendations and empty slot rendering

Phase 7 note:
- Frontend tests use Vitest with Testing Library and a `jsdom` environment.
- Page tests should usually mock the shared `frontend/src/api/index.js` entry instead of reaching through to lower-level API files, so the route layer stays aligned with the frontend API boundary introduced in Phase 5.
- Route rendering tests should use a memory router helper rather than mounting the browser entrypoint.

## Phase 8: Documentation and run instructions

- [x] Update `README.md` with frontend setup and local run commands
- [x] Update `docs/architecture.md` with React structure and frontend-backend data flow
- [x] Update `docs/assumptions.md` with frontend validation and integration assumptions
- [x] Update `docs/session-log.md` when frontend implementation milestones are completed

## Phase 9: Full-stack verification

- [x] Run backend and frontend together locally
- [x] Verify Pokemon lookup and team analysis flows end to end
- [x] Verify backend error messages are surfaced clearly in the UI
- [x] Verify the team cannot exceed 6 Pokemon
- [x] Verify mobile layout behavior for the three main views

Phase 9 note:
- Local full-stack verification required explicit backend CORS support for the frontend dev origin. The backend now reads a named allowed origin from configuration instead of assuming same-origin browser access.
- The Phase 9 runtime pass also caught and fixed the live `officialArtworkUrl` deserialization issue from PokeAPI's `official-artwork` key, so the documented frontend image contract now matches the actual backend response again.

## Notes for implementation

- The frontend must call the backend, not PokeAPI directly.
- The backend remains authoritative for validation and final error responses.
- Keep frontend state management simple unless the implemented UI proves otherwise.
- Support both `spriteUrl` and `officialArtworkUrl` from the backend.
- Use sprites for quick lookup and Team Builder views, and use official artwork for polished detailed views and Team Analysis summaries.
- Fallback from `officialArtworkUrl` to `spriteUrl` when artwork is missing.
- Keep frontend and backend team-size validation aligned at 1 to 6 Pokemon.
- Do not mark frontend tasks complete until the UI is actually built and tested.

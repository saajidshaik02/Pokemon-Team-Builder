# Tasks

## Current goal

Turn the existing Spring Boot Pokemon Team Analysis backend into a usable full-stack application by adding a React frontend without breaking the current API behavior.

## Current project state

Backend complete:
- [x] Spring Boot project scaffolded
- [x] Layered backend package structure in place
- [x] `GET /api/health` implemented
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

- [ ] Create `PokedexPage`
- [ ] Create `PokemonSearchForm` with single-name input and submit action
- [ ] Create `PokemonCard` that supports both sprite and official artwork rendering
- [ ] Create `PokemonAbilitiesList`
- [ ] Create `PokemonStatsPanel` with numeric values and progress bars
- [ ] Create `TypeBadge` component with per-type styling
- [ ] Add frontend validation for blank Pokemon names
- [ ] Call `GET /api/pokemon/{name}` through the frontend API layer
- [ ] Use `spriteUrl` for quick Pokemon search results and profile-style lookup previews
- [ ] Use `officialArtworkUrl` for polished detailed Pokemon views when available
- [ ] Fallback from `officialArtworkUrl` to `spriteUrl` when official artwork is missing
- [ ] Add image loading skeleton or loading state for Pokemon images
- [ ] Add image fallback handling for missing or broken artwork URLs
- [ ] Show loading, success, empty, and invalid-name error states

Pokedex view breakdown:
- Components needed: `PokedexPage`, `PokemonSearchForm`, `PokemonCard`, `PokemonAbilitiesList`, `PokemonStatsPanel`, `TypeBadge`, `LoadingState`, `ErrorNotice`, `ImageLoader`
- Data flow: search form -> API helper -> `GET /api/pokemon/{name}` -> normalized response stored in page state -> search and quick profile states use `spriteUrl`, detailed display can switch to `officialArtworkUrl` with `spriteUrl` fallback -> detail components render from state
- UX display: quick lookup can feel lively with sprite usage, detailed card can use official artwork for a cleaner profile view, stat bars remain readable, backend error message stays near the search form
- Step-by-step sub-tasks:
- [ ] Wire the page route and layout entry
- [ ] Build the search form and controlled input state
- [ ] Add the lookup API client method
- [ ] Add image URL resolution logic for artwork and sprite fallback
- [ ] Render image loading state before artwork resolves
- [ ] Render fetched Pokemon details on success
- [ ] Render backend error states cleanly on failure

## Phase 3: Team Builder view

- [ ] Create `TeamBuilderPage`
- [ ] Create `TeamSlotsGrid` with exactly 6 visible slots
- [ ] Create `TeamSlotCard` for sprite-first team-slot rendering, with artwork support if needed later
- [ ] Create `EmptyTeamSlot` with plus icon or add action
- [ ] Create `AddPokemonForm` for adding a Pokemon to the team
- [ ] Create `TeamValidationMessage` for duplicate, blank, and max-size errors
- [ ] Add local state for team composition and slot ordering
- [ ] Prevent adding more than 6 Pokemon
- [ ] Prevent blank entries in the UI before submit
- [ ] Use `spriteUrl` in team slots so the builder stays compact and visually active
- [ ] Keep `officialArtworkUrl` available in team member state for later use in analysis or detailed views
- [ ] Fallback cleanly if a slot sprite URL is missing or broken
- [ ] Add slot-level image loading and broken-image fallback handling
- [ ] Surface duplicate-name errors from the backend clearly

Team Builder breakdown:
- Components needed: `TeamBuilderPage`, `TeamSlotsGrid`, `TeamSlotCard`, `EmptyTeamSlot`, `AddPokemonForm`, `TypeBadge`, `TeamValidationMessage`, `ImageLoader`
- Data flow: add form -> local team state stores `name`, `spriteUrl`, and `officialArtworkUrl` when available -> slots grid renders `spriteUrl` first -> submit action passes `pokemonNames` array to analysis API
- UX display: fixed six-slot grid, empty slots visible at all times, animated or classic sprites keep the builder lively, clear disabled state when the team is full
- Step-by-step sub-tasks:
- [ ] Build six-slot grid layout
- [ ] Add team state and slot update helpers
- [ ] Add add/remove Pokemon actions
- [ ] Add inline validation for blank and over-limit cases
- [ ] Add image resolution and fallback logic for team slots
- [ ] Add summary state showing current team count
- [ ] Keep the analysis action disabled until at least one Pokemon is present

## Phase 4: Team Analysis view

- [ ] Create `TeamAnalysisPage`
- [ ] Create `TeamAnalysisForm` or integrate submit action from Team Builder
- [ ] Create `AnalysisSummaryPanel`
- [ ] Create `TypeAnalysisSection` for weaknesses, resistances, immunities, and synergy notes
- [ ] Create `RoleAnalysisSection` for roles, role counts, and summary
- [ ] Create `StatSummarySection` for totals, averages, strengths, and weaknesses
- [ ] Create `RecommendationsSection`
- [ ] Create `WeaknessList`, `ResistanceList`, and `ImmunityList` subcomponents
- [ ] Call `POST /api/team/analyze` through the frontend API layer
- [ ] Add summary rendering for team member official artwork in the analysis header, with sprite fallback if artwork is missing
- [ ] Show loading, success, empty, and backend validation failure states

Team Analysis breakdown:
- Components needed: `TeamAnalysisPage`, `AnalysisSummaryPanel`, `TypeAnalysisSection`, `RoleAnalysisSection`, `StatSummarySection`, `RecommendationsSection`, `WeaknessList`, `ResistanceList`, `ImmunityList`, `LoadingState`, `ErrorNotice`
- Data flow: team builder state or analysis form -> API helper -> `POST /api/team/analyze` -> analysis response stored in page state -> analysis header prefers cached `officialArtworkUrl`, falls back to cached `spriteUrl`, and each section reads only its slice of the response
- UX display: sectioned card layout, polished team presentation through official artwork, weaknesses shown first, role summary and recommendations easy to scan, empty recommendations state shown explicitly
- Step-by-step sub-tasks:
- [ ] Add the analyze-team API client method
- [ ] Define the frontend response model shape
- [ ] Render high-level team summary
- [ ] Render type analysis with severity emphasis for shared or major weaknesses
- [ ] Render role and stat analysis in separate cards
- [ ] Render recommendations as concise callouts

## Phase 5: Frontend state and backend integration

- [ ] Add centralized API helper module for all backend requests
- [ ] Normalize frontend error handling for `400`, `404`, `502`, and network failures
- [ ] Keep the frontend image model aligned with the backend DTO fields for `officialArtworkUrl` and `spriteUrl`
- [ ] Keep route-level state minimal and pass derived data into presentational components
- [ ] Add optimistic UI only where it improves clarity without hiding backend validation
- [ ] Add request cancellation or stale-response protection for repeated Pokemon searches
- [ ] Add shared image resolver that can return sprite-first or artwork-first behavior depending on the view
- [ ] Add shared image loading and image error state handling for Pokemon artwork
- [ ] Add reusable hooks for loading and error state if duplication appears

## Phase 6: UI polish and responsiveness

- [ ] Apply responsive layout rules for mobile, tablet, and desktop
- [ ] Use type-colored badge styles for Pokemon types
- [ ] Use sprites for Pokedex search previews and Team Builder slots
- [ ] Use official artwork for detailed Pokemon cards and Team Analysis summaries
- [ ] Add subtle card elevation, spacing, and section hierarchy for readability
- [ ] Keep navigation and forms usable on small screens
- [ ] Add empty-state messaging for no search result, no team members, and no recommendations
- [ ] Add image loading placeholders or skeletons while artwork is loading

Design direction inspired by common Pokemon tools:
- [ ] Borrow the clear searchable detail focus seen in Pokemon Database
- [ ] Borrow the dense-but-readable team workflow from Pokemon Showdown's teambuilder
- [ ] Borrow the structured stat and data grouping style common in Serebii Pokedex pages

## Phase 7: Frontend testing

- [ ] Add tests for route rendering
- [ ] Add tests for `PokemonSearchForm` behavior
- [ ] Add tests for `AddPokemonForm` and team-slot interactions
- [ ] Add tests for max-team validation and blank-entry validation
- [ ] Add tests for Pokemon lookup API success and failure states
- [ ] Add tests for team analysis API success and failure states
- [ ] Add tests for empty recommendations and empty slot rendering

## Phase 8: Documentation and run instructions

- [x] Update `README.md` with frontend setup and local run commands
- [x] Update `docs/architecture.md` with React structure and frontend-backend data flow
- [x] Update `docs/assumptions.md` with frontend validation and integration assumptions
- [x] Update `docs/session-log.md` when frontend implementation milestones are completed

## Phase 9: Full-stack verification

- [ ] Run backend and frontend together locally
- [ ] Verify health, Pokemon lookup, and team analysis flows end to end
- [ ] Verify backend error messages are surfaced clearly in the UI
- [ ] Verify the team cannot exceed 6 Pokemon
- [ ] Verify mobile layout behavior for the three main views

## Notes for implementation

- The frontend must call the backend, not PokeAPI directly.
- The backend remains authoritative for validation and final error responses.
- Keep frontend state management simple unless the implemented UI proves otherwise.
- Support both `spriteUrl` and `officialArtworkUrl` from the backend.
- Use sprites for quick lookup and Team Builder views, and use official artwork for polished detailed views and Team Analysis summaries.
- Fallback from `officialArtworkUrl` to `spriteUrl` when artwork is missing.
- Keep frontend and backend team-size validation aligned at 1 to 6 Pokemon.
- Do not mark frontend tasks complete until the UI is actually built and tested.

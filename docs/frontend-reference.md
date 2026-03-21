# Frontend Reference

## Purpose

This document is a concise code-oriented reference for the implemented React frontend under `frontend/`.
It is not a design spec. It explains the main page responsibilities, shared hooks, API helpers, and the most important component boundaries so future edits can stay fast and consistent.

## Entry points

- `frontend/src/main.jsx`
  - mounts the app with `BrowserRouter`
  - loads shared tokens and global styles

- `frontend/src/App.jsx`
  - defines the top-level routes
  - redirects `/` and unknown routes to `/pokedex`
  - wraps all pages in `AppShell`

- `frontend/src/layouts/AppShell.jsx`
  - renders the shared header, navigation, outlet, and footer
  - should stay presentation-focused

## Shared API layer

Use the shared API entrypoint instead of importing deep modules from pages:

- `frontend/src/api/index.js`
  - exports `getPokemonByName`
  - exports `normalizePokemonDetails`
  - exports `analyzeTeam`

Supporting modules:

- `frontend/src/api/httpClient.js`
  - owns the Axios instance
  - normalizes backend and network errors into a consistent frontend error shape
  - current normalized fields are `status`, `error`, `title`, `message`, `path`, and `timestamp`

- `frontend/src/api/pokemonApi.js`
  - `getPokemonByName(name)` calls `GET /api/pokemon/{name}`
  - normalizes Pokemon DTOs so the frontend keeps backend field names like `officialArtworkUrl` and `spriteUrl`

- `frontend/src/api/teamApi.js`
  - `analyzeTeam(pokemonNames)` calls `POST /api/team/analyze`
  - normalizes missing analysis sections into stable defaults

- `frontend/src/api/imageUtils.js`
  - `resolvePokemonImage(pokemon, mode)` returns `primarySrc`, `fallbackSrc`, and `alt`
  - supports `sprite-first` and `artwork-first`
  - quick views use sprite-first
  - detail and analysis views use artwork-first

## Shared hooks

- `frontend/src/hooks/useApi.js`
  - wraps async API calls with `data`, `error`, and `isLoading`
  - exposes `execute`, `setData`, and `setError`
  - supports `ignoreStaleResponses` for repeated searches
  - use this for route-level request state unless a task clearly needs something more specialized

- `frontend/src/hooks/useFormState.js`
  - small helper for controlled form state
  - exposes `values`, `setValues`, `updateValue`, and `resetValues`
  - accepts either an event object or an explicit `name, value` pair

## Shared common components

- `frontend/src/components/common/LoadingState.jsx`
  - generic loading card

- `frontend/src/components/common/ErrorNotice.jsx`
  - generic error card for user-facing API or validation failures

- `frontend/src/components/common/ImageLoader.jsx`
  - local image loading and fallback state
  - shows skeleton loading state until the image resolves
  - falls back to the secondary source when the primary source fails
  - shows a simple empty frame when no source is available

## Route pages

### `PokedexPage`

File:
- `frontend/src/pages/PokedexPage.jsx`

Responsibilities:
- owns the single-name lookup form state
- validates blank input on the client
- calls `getPokemonByName`
- uses `useApi(..., { ignoreStaleResponses: true })` to avoid stale repeated-search updates
- renders:
  - loading state
  - validation state
  - `404` no-result empty state
  - generic backend failure state
  - quick sprite preview
  - detailed artwork-first Pokemon card

Main child components:
- `PokemonSearchForm`
- `PokemonCard`
- `TypeBadge`
- `ImageLoader`

### `TeamBuilderPage`

File:
- `frontend/src/pages/TeamBuilderPage.jsx`

Responsibilities:
- owns the six-slot local team state
- validates:
  - blank names
  - duplicate names
  - max team size of 6
- calls `getPokemonByName` before adding a Pokemon to the team
- stores `name`, `types`, `spriteUrl`, and `officialArtworkUrl` in local team state
- removes team members locally
- navigates to `/team-analysis` with route state

Main child components:
- `AddPokemonForm`
- `TeamValidationMessage`
- `TeamSlotsGrid`
- `TeamSlotCard`
- `EmptyTeamSlot`

### `TeamAnalysisPage`

File:
- `frontend/src/pages/TeamAnalysisPage.jsx`

Responsibilities:
- accepts direct manual team input or route-state handoff from Team Builder
- validates team size on the client before submit
- calls `analyzeTeam`
- resolves missing summary visuals with backend Pokemon lookups instead of calling PokeAPI from the browser
- keeps a keyed summary visual cache instead of duplicating large derived arrays in route state
- renders:
  - loading state
  - backend validation or failure state
  - summary panel
  - type analysis
  - role analysis
  - stat summary
  - recommendations
  - empty state when no result is active

Main child components:
- `TeamAnalysisForm`
- `AnalysisSummaryPanel`
- `TypeAnalysisSection`
- `RoleAnalysisSection`
- `StatSummarySection`
- `RecommendationsSection`

## Pokemon components

- `PokemonSearchForm`
  - controlled single-name form for Pokedex lookups

- `PokemonCard`
  - detailed Pokemon view
  - prefers official artwork and falls back to sprite
  - renders types, abilities, and stats

- `PokemonAbilitiesList`
  - formats ability names into readable labels

- `PokemonStatsPanel`
  - renders numeric stat rows and progress bars

- `TypeBadge`
  - renders type-colored labels

## Team components

- `AddPokemonForm`
  - controlled form for adding a Pokemon to the builder

- `TeamValidationMessage`
  - renders warning, error, or success copy for builder interactions

- `TeamSlotsGrid`
  - always renders exactly 6 positions
  - delegates to `TeamSlotCard` or `EmptyTeamSlot`

- `TeamSlotCard`
  - sprite-first slot display with remove action

- `EmptyTeamSlot`
  - placeholder for unfilled team positions

## Analysis components

- `TeamAnalysisForm`
  - textarea-based manual team submission

- `AnalysisSummaryPanel`
  - artwork-first analyzed team header

- `TypeAnalysisSection`
  - weaknesses first, then resistances, immunities, and synergy notes

- `RoleAnalysisSection`
  - assigned roles, role counts, and summary

- `StatSummarySection`
  - totals, averages, strengths, and weaknesses

- `RecommendationsSection`
  - recommendation callouts or explicit empty state

- `WeaknessList`, `ResistanceList`, `ImmunityList`
  - focused list renderers for the type-analysis section

## Styling and layout

- `frontend/src/styles/tokens.css`
  - shared design tokens

- `frontend/src/styles/globals.css`
  - main app styling
  - shared card and status styles
  - page-level responsive layout modifiers such as:
    - `page-grid--pokedex`
    - `page-grid--team-builder`
    - `page-grid--team-analysis`

Current layout rule:
- keep most styling in shared global styles
- add page-level modifier classes only when a route needs a distinct layout composition

## Testing reference

Frontend tests live under `frontend/src` alongside the code they cover.

Key files:
- `frontend/src/test/setup.js`
- `frontend/src/test/renderWithRouter.jsx`
- `frontend/src/App.test.jsx`
- page and component `*.test.jsx` files

Current test approach:
- use Vitest plus Testing Library
- use `MemoryRouter` helpers for route-aware rendering
- mock the shared `frontend/src/api/index.js` module in page tests
- assert user-visible behavior instead of Axios internals

## Practical edit rules

- Import API functions from `frontend/src/api/index.js` in route pages.
- Keep PokeAPI access in the backend only.
- Keep frontend validation UX-focused, but do not replace backend validation.
- Preserve the backend DTO field names `officialArtworkUrl` and `spriteUrl`.
- Prefer `useApi` and `useFormState` before adding heavier frontend state abstractions.
- Keep route pages responsible for data fetching and high-level state, and keep child components presentation-focused.

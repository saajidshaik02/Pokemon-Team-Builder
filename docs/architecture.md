# Architecture

## Product goal

Create a Pokemon team analysis tool that helps users:
- look up a single Pokemon by name
- build and analyze a team of up to 6 Pokemon
- understand shared weaknesses, resistances, and immunities
- review simple role and stat balance
- receive deterministic recommendations for improving team balance

The project is intentionally lightweight. It focuses on explainable analysis rather than full competitive battle simulation.

## Current project status

Implemented now:
- Spring Boot backend API under `backend/`
- health endpoint
- single Pokemon lookup endpoint
- team analysis endpoint
- type, role, stat, and recommendation logic
- backend tests for controllers, services, and mappers

Planned next:
- React frontend under `frontend/`
- Pokedex view
- Team Builder view
- Team Analysis view
- responsive layout and frontend validation

## Scope

Supported scope:
- Pokemon lookup by name
- team analysis for 1 to 6 Pokemon
- PokeAPI-backed Pokemon data loading through the backend
- normalized Pokemon data returned by the backend
- frontend display of official artwork when available and sprite fallback when needed
- aggregated weaknesses, resistances, immunities, and synergy notes
- simple role classification from base stats
- stat summary totals and averages
- deterministic recommendations
- frontend consumption of backend REST endpoints

Out of scope:
- battle simulation
- movesets
- EVs and IVs
- held items
- ability mechanics in battle
- natures
- breeding or progression systems
- authentication
- persistence or database storage

## Tech stack

Backend:
- Java 21
- Spring Boot
- Maven
- PokeAPI as the external data source
- JUnit and Spring Boot Test

Frontend:
- React
- Vite
- React Router
- Axios
- CSS modules or scoped CSS files with shared design tokens

## Design inspiration

The frontend should take cues from a few widely used Pokemon tools while staying simpler than them:
- Pokemon Showdown teambuilder: good reference for fixed team slots, quick add or remove workflows, and analysis-focused structure
- Pokemon Database Pokedex pages: good reference for searchable Pokemon detail views, readable stat presentation, and type labels
- Serebii Pokedex pages: good reference for grouping sprite, abilities, typing, and stat information in compact sections

The goal is not to copy those interfaces directly. The goal is to reuse the strongest patterns:
- searchable single-Pokemon detail flow
- visible six-slot team builder grid
- sectioned analysis output with clear visual hierarchy

## High-level system design

The application is a small full-stack system with a strict boundary:

1. The React frontend accepts user input and manages view state.
2. The frontend validates basic form constraints and sends requests to the backend.
3. The Spring Boot backend validates the request again, normalizes names, and loads Pokemon data from PokeAPI.
4. The backend maps external responses into internal DTOs, runs analysis services, and returns structured JSON.
5. The frontend resolves Pokemon image display from backend-provided URLs, preferring official artwork and falling back to classic sprites.
6. The frontend renders loading, success, empty, and error states for the user.

Architecture flow:

`React UI -> frontend API layer -> Spring controller -> service -> PokeAPI client -> mapper -> analysis services -> response DTO -> React UI`

## Main responsibilities by layer

### Frontend layer

Responsible for:
- route-level views
- form input and client-side validation
- loading and error states
- Pokemon image loading and fallback state handling
- rendering Pokemon details and team analysis results
- maintaining team composition state
- calling backend endpoints only

### Controller layer

Responsible for:
- receiving HTTP requests
- delegating to services
- returning structured API responses

### Service layer

Responsible for:
- Pokemon lookup orchestration
- request validation and normalization
- team analysis orchestration
- deterministic recommendation generation

### Client layer

Responsible for:
- calling PokeAPI
- handling upstream API failures cleanly
- returning raw external models only inside the backend

### Mapper layer

Responsible for:
- converting PokeAPI responses into normalized internal DTOs

### Model layer

Responsible for:
- internal analysis support structures such as the type-effectiveness chart

### DTO layer

Responsible for:
- explicit request and response contracts between backend and frontend

### Exception layer

Responsible for:
- consistent JSON error payloads for invalid requests, missing Pokemon, upstream failures, and unexpected errors

## Repository structure

```text
backend/
  src/main/java/com/example/pokemon/
    controller/
    service/
    client/
    mapper/
    dto/
    model/
    exception/
    config/
    PokemonApplication.java
  src/main/resources/
  src/test/java/com/example/pokemon/

frontend/
  src/
    api/
      pokemonApi.js
      teamApi.js
    components/
      common/
      pokemon/
      team/
      analysis/
    hooks/
    layouts/
    pages/
      PokedexPage.jsx
      TeamBuilderPage.jsx
      TeamAnalysisPage.jsx
    styles/
      tokens.css
      globals.css
    App.jsx
    main.jsx

docs/
  architecture.md
  assumptions.md
  session-log.md
  tasks.md
```

The `frontend/` structure above is the planned target layout for the next phase. It is not scaffolded in the current repository yet.

## Backend API contract

### Health check

`GET /api/health`

Returns backend availability:

```json
{
  "status": "ok"
}
```

### Pokemon lookup

`GET /api/pokemon/{name}`

Returns normalized Pokemon data:

```json
{
  "id": 25,
  "name": "pikachu",
  "types": ["electric"],
  "abilities": ["static", "lightning-rod"],
  "stats": {
    "hp": 35,
    "attack": 55,
    "defense": 40,
    "specialAttack": 50,
    "specialDefense": 50,
    "speed": 90
  },
  "spriteUrl": "..."
}
```

The current backend contract exposes `spriteUrl`.
If the frontend is expected to use official artwork without calling PokeAPI directly, the backend should be extended to expose `officialArtworkUrl` alongside `spriteUrl`.

### Team analysis

`POST /api/team/analyze`

Request:

```json
{
  "pokemonNames": ["pikachu", "charizard", "blastoise"]
}
```

Response sections:
- `team`
- `typeAnalysis`
- `roleAnalysis`
- `statSummary`
- `recommendations`

## Validation and error handling

Backend validation rules:
- Pokemon names must not be blank
- team size must be between 1 and 6
- duplicate Pokemon are currently rejected
- invalid Pokemon names return `404`
- malformed JSON returns `400`
- upstream PokeAPI failures return `502`

Error payload shape:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Pokemon team must contain between 1 and 6 Pokemon.",
  "path": "/api/team/analyze",
  "timestamp": "2026-03-21T00:00:00Z"
}
```

The frontend should consume this error format directly and surface the `message` value in user-facing error states.

## Frontend UI structure

### App shell

The frontend should use a simple shell with:
- top navigation for `Pokedex`, `Team Builder`, and `Team Analysis`
- a centered responsive content container
- reusable section cards and status messages

### Pokedex view

Main components:
- `PokedexPage`
- `PokemonSearchForm`
- `PokemonCard`
- `PokemonAbilitiesList`
- `PokemonStatsPanel`
- `TypeBadge`
- `PokemonImage`

Data flow:
- user enters a Pokemon name
- `PokemonSearchForm` triggers frontend API call
- frontend calls `GET /api/pokemon/{name}`
- page state stores loading, error, and Pokemon detail data
- image state resolves `officialArtworkUrl` first and `spriteUrl` second once the backend contract includes both fields
- detail subcomponents render from the response DTO

UX behavior:
- show an image loading placeholder or skeleton before artwork finishes loading
- prefer official artwork for the main Pokemon visual
- fall back to classic sprite imagery if official artwork is unavailable
- display type badges with type-specific colors
- display stats as values and progress bars
- show abilities in a simple readable list
- surface invalid-name errors near the search field

### Team Builder view

Main components:
- `TeamBuilderPage`
- `TeamSlotsGrid`
- `TeamSlotCard`
- `EmptyTeamSlot`
- `AddPokemonForm`
- `PokemonImage`
- `TeamValidationMessage`

Data flow:
- user adds or removes Pokemon locally
- page stores the team array in route-level state
- slot image state resolves official artwork first and sprite fallback second for each filled slot
- the six-slot grid renders current team members plus empty slots
- submit action sends the ordered Pokemon name list to the analysis flow

UX behavior:
- always show exactly 6 slots
- show a plus-style empty state for unused slots
- prevent adding more than 6 Pokemon on the client
- show a clear message when the team is full
- keep artwork or sprite thumbnails and names easy to scan in grid form

### Team Analysis view

Main components:
- `TeamAnalysisPage`
- `AnalysisSummaryPanel`
- `TypeAnalysisSection`
- `RoleAnalysisSection`
- `StatSummarySection`
- `RecommendationsSection`
- `WeaknessList`
- `ResistanceList`
- `ImmunityList`

Data flow:
- frontend submits `pokemonNames` to `POST /api/team/analyze`
- analysis response is stored in page state
- team summary image state resolves official artwork first and sprite fallback second using cached lookup data or additional frontend-side team detail state
- each section receives only the relevant slice of the response
- errors from invalid teams or upstream failures are shown in a shared error component

UX behavior:
- put weaknesses first because they are the highest-priority result
- group resistances and immunities separately for readability
- display role labels in compact cards or chips
- show synergy notes and recommendations as concise callouts
- show an explicit empty state when recommendations are absent

## Frontend-backend interaction model

Recommended API helpers:
- `getPokemonByName(name)`
- `analyzeTeam(pokemonNames)`
- `getHealthStatus()`

Recommended image helper behavior:
- `resolvePokemonImage(pokemon)` should prefer `pokemon.officialArtworkUrl` once that field exists in the backend DTO
- otherwise use `pokemon.spriteUrl`
- image components should keep local loading and error flags so broken artwork can downgrade to the fallback source cleanly

Recommended state ownership:
- `PokedexPage`: search term, Pokemon result, loading, error, image loading state
- `TeamBuilderPage`: team array, validation message, slot image loading state
- `TeamAnalysisPage`: submitted team, analysis result, loading, error, summary image loading state

Recommended shared UI primitives:
- `TypeBadge`
- `PokemonImage`
- `LoadingState`
- `ErrorNotice`
- `SectionCard`

## Styling guidance

Use simple CSS with shared design tokens first. Material UI is optional, but plain CSS is enough for this project if the component structure stays clean.

Suggested visual rules:
- use colored type badges for quick scanning
- use card sections with consistent padding and subtle shadow
- use official artwork for primary visuals and classic sprites as fallback visuals
- use a 2-column layout on desktop and a single-column stack on mobile
- keep interactive controls large enough for touch devices
- keep contrast high and labels explicit

## Key design decisions

- The backend remains the only system that talks to PokeAPI.
- The frontend stays presentation-focused and should not duplicate backend business logic.
- API contracts remain DTO-driven and explicit.
- Analysis logic remains deterministic and explainable.
- No database, authentication, or persistence should be added unless requirements change.

## Success criteria

A successful next iteration should:
- keep the existing backend behavior stable
- add a responsive React frontend that consumes the live backend
- allow users to look up a Pokemon and analyze a team without using Postman
- display artwork or sprite loading states, fallback image handling, and backend errors clearly
- remain small, readable, and easy to extend

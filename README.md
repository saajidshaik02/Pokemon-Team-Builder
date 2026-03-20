# Pokemon Team Builder

Pokemon Team Builder is a Pokemon team analysis project with:
- a completed Spring Boot backend in `backend/`
- a planned React frontend in `frontend/`

The backend already supports Pokemon lookup and team analysis through REST endpoints. The next implementation phase is building the React UI on top of those endpoints.

## Current status

Implemented now:
- `GET /api/health`
- `GET /api/pokemon/{name}`
- `POST /api/team/analyze`
- deterministic type, role, stat, and recommendation analysis
- consistent JSON error handling

Planned frontend views:
- Pokedex
- Team Builder
- Team Analysis

## UI direction

The planned frontend takes inspiration from a few well-known Pokemon tools:
- Pokemon Showdown teambuilder for fast team slot workflows
- Pokemon Database Pokedex pages for readable single-Pokemon detail views
- Serebii Pokedex pages for compact data grouping and stat presentation

The frontend should use those patterns in a lighter way:
- searchable Pokemon detail card
- six visible team slots with artwork or sprite fallback
- sectioned analysis layout with weaknesses, roles, synergy notes, and recommendations

## Repository structure

```text
backend/   Spring Boot API
docs/      Architecture, assumptions, tasks, and session log
frontend/  Planned React application, not scaffolded yet
```

## Backend structure

The Spring Boot backend lives under `backend/` and follows the layered architecture documented in `docs/architecture.md`.

- `backend/src/main/java/com/example/pokemon/controller`: REST controllers
- `backend/src/main/java/com/example/pokemon/service`: business logic services
- `backend/src/main/java/com/example/pokemon/client`: PokeAPI client and raw upstream models
- `backend/src/main/java/com/example/pokemon/mapper`: mapping from upstream models to DTOs
- `backend/src/main/java/com/example/pokemon/dto`: API request and response DTOs
- `backend/src/main/java/com/example/pokemon/model`: internal analysis helpers
- `backend/src/main/java/com/example/pokemon/exception`: custom exceptions and global error handling
- `backend/src/main/java/com/example/pokemon/config`: Spring configuration
- `backend/src/test/java/com/example/pokemon`: controller, service, and mapper tests

## Available backend endpoints

- `GET /api/health`
- `GET /api/pokemon/{name}`
- `POST /api/team/analyze`

## Run the backend locally

Prerequisites:
- Java 21 installed and available in `PATH`
- Maven 3.9+ installed and available in `PATH`

Run:

```bash
cd backend
mvn test
mvn spring-boot:run
```

The backend runs on `http://localhost:8080` by default.

## Planned frontend stack

- React
- Vite
- React Router
- Axios
- CSS with shared design tokens and responsive layouts

## Planned frontend structure

```text
frontend/
  src/
    api/
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
    App.jsx
    main.jsx
```

## Planned frontend component breakdown

### Pokedex

Main components:
- `PokedexPage`
- `PokemonSearchForm`
- `PokemonCard`
- `PokemonAbilitiesList`
- `PokemonStatsPanel`
- `TypeBadge`
- `PokemonImage`

Behavior:
- search by Pokemon name
- call `GET /api/pokemon/{name}`
- prefer backend-provided `officialArtworkUrl` and fallback to `spriteUrl` when needed
- show types, abilities, and stats
- show backend error messages for invalid names

### Team Builder

Main components:
- `TeamBuilderPage`
- `TeamSlotsGrid`
- `TeamSlotCard`
- `EmptyTeamSlot`
- `AddPokemonForm`
- `TeamValidationMessage`

Behavior:
- display 6 visible slots
- allow add and remove actions
- prevent adding more than 6 Pokemon
- use official artwork when available and sprite fallback for filled slots
- use plus-style empty slot visuals for unfilled slots

### Team Analysis

Main components:
- `TeamAnalysisPage`
- `TypeAnalysisSection`
- `RoleAnalysisSection`
- `StatSummarySection`
- `RecommendationsSection`
- `AnalysisSummaryPanel`

Behavior:
- call `POST /api/team/analyze`
- show weaknesses, resistances, immunities, role analysis, synergy notes, stat summary, and recommendations
- show clear loading and error states

## Running the frontend locally

These commands are for the planned React app once `frontend/` is scaffolded:

```bash
cd frontend
npm install
npm run dev
```

Recommended local setup:
1. Start the backend on `http://localhost:8080`.
2. Start the frontend development server from `frontend/`.
3. Configure the frontend base API URL through an environment variable such as `VITE_API_BASE_URL=http://localhost:8080`.
4. Prefer backend-provided `officialArtworkUrl` once that field exists, and fallback to `spriteUrl` if artwork is missing.

## API examples

### Health

Request:

```http
GET /api/health
```

Response:

```json
{
  "status": "ok"
}
```

### Pokemon lookup

Request:

```http
GET /api/pokemon/pikachu
```

Response:

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
  "spriteUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
}
```

The current backend response exposes `spriteUrl`.
If the frontend continues to call only the backend, the backend should be extended to expose both `officialArtworkUrl` and `spriteUrl` so the UI can prefer artwork and fallback to sprites cleanly.

### Team analysis

Request:

```http
POST /api/team/analyze
Content-Type: application/json
```

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

## Error response format

All backend errors use the same JSON shape:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Pokemon team must contain between 1 and 6 Pokemon.",
  "path": "/api/team/analyze",
  "timestamp": "2026-03-21T00:00:00Z"
}
```

Current backend behavior:
- blank Pokemon name -> `400`
- invalid Pokemon name -> `404`
- empty or oversized team -> `400`
- duplicate Pokemon in a team -> `400`
- malformed JSON -> `400`
- upstream PokeAPI failure -> `502`

## Styling suggestion

Simple CSS is enough for this project. Material UI is optional, but not required.

Recommended styling approach:
- define CSS variables for Pokemon type colors and spacing
- use card-based sections with subtle borders or shadow
- use badge-style type labels
- use a responsive grid for the 6 team slots
- use official artwork for primary visuals and classic sprites as fallback visuals

## Manual backend testing

You can manually test the API with Postman or `curl`.

Recommended checks:
- `GET http://localhost:8080/api/health`
- `GET http://localhost:8080/api/pokemon/pikachu`
- `POST http://localhost:8080/api/team/analyze`

## Documentation

- [Architecture](docs/architecture.md)
- [Assumptions](docs/assumptions.md)
- [Tasks](docs/tasks.md)
- [Session Log](docs/session-log.md)

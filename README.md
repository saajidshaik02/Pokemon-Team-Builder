# Pokemon Team Builder

Pokemon Team Builder is a full-stack Pokemon team analysis project with:
- a Spring Boot backend in `backend/`
- a React frontend in `frontend/`

The application now supports live Pokemon lookup, six-slot team building, backend-driven team analysis, frontend testing, and local full-stack verification.

## What the App Does

The product has three main user flows:
- `Pokedex`: search one Pokemon and view its typing, abilities, and base stats
- `Team Builder`: assemble a six-slot lineup and adjust the order
- `Team Analysis`: review shared weaknesses, resistances, immunities, role balance, stat spread, and recommendations

At a high level, the flow is:
1. the frontend collects user input
2. the frontend calls the backend REST API
3. the backend looks up Pokemon data and runs deterministic team analysis
4. the frontend renders the results in Pokedex, Team Builder, and Team Analysis views

## Current Status

Implemented now:
- `GET /api/pokemon/{name}`
- `POST /api/team/analyze`
- deterministic type, role, stat, and recommendation analysis
- centralized backend heuristic thresholds for analysis rules
- consistent JSON error handling
- Springdoc OpenAPI docs for manual API testing
- Pokedex, Team Builder, and Team Analysis frontend routes
- responsive frontend UI with sprite-first and artwork-first image handling
- Vitest and Testing Library frontend coverage
- local backend CORS support for browser-based frontend development

## Repository Structure

```text
backend/   Spring Boot API
docs/      Architecture, assumptions, frontend reference, tasks, and session log
frontend/  React frontend application
```

## Backend Structure

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

## Frontend Structure

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

## UI Direction

The frontend takes inspiration from a few well-known Pokemon tools:
- Pokemon Showdown teambuilder for fast team slot workflows
- Pokemon Database Pokedex pages for readable single-Pokemon detail views
- Serebii Pokedex pages for compact data grouping and stat presentation

The frontend should use those patterns in a lighter way:
- searchable Pokemon detail card
- six visible team slots with artwork or sprite fallback
- sectioned analysis layout with weaknesses, roles, synergy notes, and recommendations

## Backend Endpoints

- `GET /api/pokemon/{name}`
- `POST /api/team/analyze`

## Run the Backend

Prerequisites:
- Java 21 installed and available in `PATH`
- Maven 3.9+ installed and available in `PATH`

### Install Java 21 and Maven 3.9+ on Windows

Java 21:
1. Download a JDK 21 build from Oracle JDK, Eclipse Temurin, or Microsoft Build of OpenJDK.
2. Install it to a folder such as `C:\Program Files\Eclipse Adoptium\jdk-21`.
3. Add a `JAVA_HOME` environment variable that points to that JDK folder.
4. Add `%JAVA_HOME%\bin` to your `Path`.

Maven 3.9+:
1. Download the Apache Maven 3.9.x binary zip from the Apache Maven website.
2. Extract it to a folder such as `C:\Tools\apache-maven-3.9.11`.
3. Add a `MAVEN_HOME` environment variable that points to that Maven folder.
4. Add `%MAVEN_HOME%\bin` to your `Path`.

How to update `Path` on Windows:
1. Open Start and search for `Environment Variables`.
2. Open `Edit the system environment variables`.
3. Click `Environment Variables`.
4. Add or update:
   - `JAVA_HOME`
   - `MAVEN_HOME`
5. Edit `Path` and add:
   - `%JAVA_HOME%\bin`
   - `%MAVEN_HOME%\bin`
6. Close and reopen PowerShell.

Verify:

```bash
java -version
javac -version
mvn -version
```

Expected:
- `java -version` shows Java 21
- `mvn -version` shows Maven 3.9+ and a Java 21 runtime

Run:

```bash
cd backend
mvn test
mvn spring-boot:run
```

The backend runs on `http://localhost:8080` by default.

For local browser-based frontend use, the backend now allows one configured frontend origin through
`pokemon.frontend.allowed-origin` in `backend/src/main/resources/application.yml`.

Swagger and OpenAPI:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Test the Backend in Swagger

1. Start the backend:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
2. Open Swagger UI:
   - `http://localhost:8080/swagger-ui.html`
3. Use `Try it out` on:
   - `GET /api/pokemon/{name}`
   - `POST /api/team/analyze`
4. Example inputs:
   - Pokemon lookup: `pikachu`
   - Team analysis body:
     ```json
     {
       "pokemonNames": ["pikachu", "charizard", "blastoise"]
     }
     ```

## Frontend Stack

- React
- Vite
- React Router
- Axios
- Vitest
- Testing Library
- CSS with shared design tokens and responsive layouts

Vite is used because it provides a faster development server, lighter setup, and simpler build configuration for this project than older React scaffolds.

## Frontend Pages

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
- keep empty slots visible at all times

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

## Run the Frontend

Use these commands from `frontend/`:
```bash
cd frontend
npm install
npm run dev
```

Recommended local setup:
1. Start the backend on `http://localhost:8080`.
2. Start the frontend development server from `frontend/`.
3. Configure the frontend base API URL through an environment variable such as `VITE_API_BASE_URL=http://localhost:8080`.
4. Keep the backend CORS allow-origin aligned with the frontend dev origin. The current local verification setup used `http://127.0.0.1:4173`.

The frontend dev server configuration is now centralized in `frontend/vite.config.js`, so the intended command is simply:
```bash
cd frontend
npm run dev
```

That command always targets:
- host `127.0.0.1`
- port `4173`
- `strictPort: true`

Verification commands:

```bash
cd frontend
npm run lint
npm test
npm run build
```

## Full Local Run Flow

Single-command option from the repo root:

```bash
.\start-dev.cmd
```

That script opens two PowerShell windows:
- backend on `http://localhost:8080`
- frontend on `http://127.0.0.1:4173`

It also keeps the current verified local-origin pairing aligned with backend CORS and the frontend API base URL.
If either port is already in use, the script skips starting a duplicate process.
It also opens `http://127.0.0.1:4173` in Chrome when Chrome is installed, with a normal browser fallback otherwise.
The frontend startup command used by the launcher is the same `npm run dev` command defined in `frontend/package.json` and configured through `frontend/vite.config.js`.
Do not run `npm run dev` manually at the same time as `.\start-dev.cmd`, because both target the same frontend process on `4173`.

If frontend dependencies are already installed and you want to skip `npm install`:

```bash
.\start-dev.cmd -SkipInstall
```

If you want to skip the automatic browser launch:

```bash
.\start-dev.cmd -SkipInstall -NoBrowser
```

Manual option:

1. Start the backend:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
2. Open Swagger UI if you want to test backend endpoints directly:
   - `http://localhost:8080/swagger-ui.html`
3. Start the frontend:
   ```bash
   cd frontend
   npm run dev
   ```
4. Open the app:
   - `http://127.0.0.1:4173`
5. Test the user flows:
   - search a Pokemon in Pokedex
   - build a team in Team Builder
   - analyze the team in Team Analysis

## API Examples

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
  "officialArtworkUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
  "spriteUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
}
```

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

## Styling Suggestion

Simple CSS is enough for this project. Material UI is optional, but not required.

Recommended styling approach:
- define CSS variables for Pokemon type colors and spacing
- use card-based sections with subtle borders or shadow
- use badge-style type labels
- use a responsive grid for the 6 team slots
- use official artwork for primary visuals and classic sprites as fallback visuals

## Documentation

- [Architecture](docs/architecture.md)
- [Assumptions](docs/assumptions.md)
- [Frontend Reference](docs/frontend-reference.md)
- [Tasks](docs/tasks.md)
- [Session Log](docs/session-log.md)

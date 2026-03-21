# AGENTS

## Repository expectations

This repository now contains:
- a completed Spring Boot backend under `backend/`
- a planned React frontend under `frontend/`

Follow these rules for every task:

- Follow the architecture in `docs/architecture.md`.
- When review feedback introduces a rule that should guide future work, document it in the relevant markdown file instead of leaving it implicit in one patch.
- Keep the application split cleanly between the React frontend and Spring Boot backend.
- Frontend code must call the backend REST API. Do not call PokeAPI directly from the browser.
- Do not add a database, authentication, or persistence unless explicitly requested.
- Do not implement full Pokemon battle simulation, movesets, EVs, IVs, held items, or ability mechanics.
- Prefer simple, deterministic, explainable analysis rules over complex competitive logic.
- Keep backend package structure clean and layered: `controller`, `service`, `client`, `mapper`, `dto`, `model`, `exception`, `config`.
- Keep frontend structure clean and focused, with clear separation between pages, components, API utilities, and styles.
- Use DTOs for backend API contracts. Do not expose raw PokeAPI responses directly from controllers.
- Keep methods and classes focused. Avoid oversized files and duplicated logic.
- Validate user input on both frontend and backend.
- Handle loading failures from PokeAPI and backend API failures gracefully.
- Normalize Pokemon names to lowercase before lookup on the backend.
- Reject invalid teams that contain fewer than 1 or more than 6 Pokemon.
- Keep numeric backend analysis thresholds centralized in named shared config or constants, and prefer YAML-backed config when those values may need tuning later.
- Prefer modifying existing files over creating duplicate versions.
- Before making major structural changes, explain the proposed changes first.
- Add or update tests when introducing non-trivial business logic or frontend state behavior.
- Keep naming explicit and consistent across controllers, services, DTOs, models, React components, and frontend API utilities.

## Coding style

- Write readable, maintainable code.
- Prefer clear names over clever abstractions.
- Keep backend business logic in services, not controllers.
- Keep external API calling logic in the backend client layer.
- Keep backend mapping logic in mapper classes.
- Keep frontend API calling logic in dedicated API helper modules.
- Keep recommendation logic deterministic and easy to explain.
- Keep backend OpenAPI or Swagger docs in sync with the live REST endpoints so manual API testing stays easy.
- Keep the UI responsive, mobile-friendly, and simple to understand.

## Analysis rules

The current supported scope should remain:
- single Pokemon lookup
- team analysis for up to 6 Pokemon
- type weakness and resistance aggregation
- immunity aggregation
- simple role classification based on base stats
- simple recommendation generation based on shared weaknesses, role imbalance, and stat imbalance

Use simple heuristics. Do not over-engineer.

## Testing expectations

When changing business logic:
- add or update unit tests where appropriate
- keep tests focused and readable

When adding frontend behavior:
- add or update component and API interaction tests where appropriate
- keep validation and error-state coverage focused on user-visible behavior

## Workflow expectations

After completing a task:
- summarize what changed
- list any assumptions made
- mention any follow-up work that is still needed

When a task is completed:
- update `docs/tasks.md` to reflect completed checklist items
- update `docs/session-log.md` with a short summary of work completed
- update `README.md` if API usage, frontend setup, or run instructions changed
- do not mark items complete unless they are actually working

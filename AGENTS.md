# AGENTS

# AGENTS.md

## Repository expectations

This repository contains a backend-only Pokémon team analysis tool built with Java 21 and Spring Boot.

Follow these rules for every task:

- Follow the architecture in `docs/architecture.md`.
- Keep the codebase backend-only unless explicitly told to add a frontend.
- Do not add a database, authentication, or persistence unless explicitly requested.
- Do not implement full Pokémon battle simulation, movesets, EVs, IVs, held items, or ability mechanics.
- Prefer simple, deterministic, explainable analysis rules over complex competitive logic.
- Keep package structure clean and layered: `controller`, `service`, `client`, `mapper`, `dto`, `model`, `exception`, `config`.
- Use DTOs for API contracts. Do not expose raw PokéAPI responses directly from controllers.
- Keep methods and classes focused. Avoid oversized files and duplicated logic.
- Validate request payloads and return clear error responses.
- Handle loading failures from PokéAPI gracefully.
- Normalize Pokémon names to lowercase before lookup.
- Reject invalid teams that contain fewer than 1 or more than 6 Pokémon.
- Prefer modifying existing files over creating duplicate versions.
- Before making major structural changes, explain the proposed changes first.
- Add or update tests when introducing non-trivial business logic.
- Keep naming explicit and consistent across controllers, services, DTOs, and models.

## Coding style

- Write readable, maintainable Java.
- Prefer clear names over clever abstractions.
- Keep business logic in services, not controllers.
- Keep external API calling logic in the client layer.
- Keep mapping logic in mapper classes.
- Keep recommendation logic deterministic and easy to explain.

## Analysis rules

The first version should only support:
- single Pokémon lookup
- team analysis for up to 6 Pokémon
- type weakness and resistance aggregation
- simple role classification based on base stats
- simple recommendation generation based on shared weaknesses or role imbalance

Use simple heuristics. Do not over-engineer.

## Testing expectations

When changing business logic:
- add or update unit tests where appropriate
- keep tests focused and readable

## Workflow expectations

After completing a task:
- summarize what changed
- list any assumptions made
- mention any follow-up work that is still needed

When a task is completed:
- update docs/tasks.md to reflect completed checklist items
- update docs/session-log.md with a short summary of work completed
- update README.md if API usage or run instructions changed
- do not mark items complete unless they are actually working

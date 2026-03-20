# Tasks

## Current goal
Build a backend-only Pokemon team analysis tool using Spring Boot.

## Phase 1: Project setup
- [x] Create Spring Boot project
- [x] Set up package structure
- [x] Add `/api/health` endpoint
- [x] Confirm project runs locally

## Phase 2: Pokemon lookup
- [x] Add PokéAPI client
- [x] Add `GET /api/pokemon/{name}` endpoint
- [x] Normalize Pokemon response into internal DTO
- [x] Handle invalid Pokemon names cleanly

## Phase 3: Team analysis
- [x] Add `POST /api/team/analyze` endpoint
- [x] Validate team size is between 1 and 6
- [x] Fetch all Pokemon in submitted team
- [x] Aggregate type weaknesses
- [x] Aggregate resistances and immunities
- [x] Return structured analysis response

## Phase 4: Role and stat analysis
- [x] Add stat summary logic
- [x] Add simple role classification
- [x] Detect role imbalance
- [x] Add recommendation generation

## Phase 5: Error handling and polish
- [x] Add global exception handling
- [x] Improve response messages
- [x] Clean up DTO naming
- [x] Refactor duplicated logic

## Phase 6: Testing
- [x] Add tests for Pokemon lookup service
- [x] Add tests for team validation
- [x] Add tests for type analysis logic
- [x] Add tests for recommendation logic

## Phase 7: Submission prep
- [x] Review architecture alignment
- [x] Update README
- [x] Update assumptions
- [x] Update session log
- [x] Final manual endpoint testing

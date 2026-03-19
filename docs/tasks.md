# Tasks

## Current goal
Build a backend-only Pokémon team analysis tool using Spring Boot.

## Phase 1: Project setup
- [ ] Create Spring Boot project
- [ ] Set up package structure
- [ ] Add `/api/health` endpoint
- [ ] Confirm project runs locally

## Phase 2: Pokémon lookup
- [ ] Add PokéAPI client
- [ ] Add `GET /api/pokemon/{name}` endpoint
- [ ] Normalize Pokémon response into internal DTO
- [ ] Handle invalid Pokémon names cleanly

## Phase 3: Team analysis
- [ ] Add `POST /api/team/analyze` endpoint
- [ ] Validate team size is between 1 and 6
- [ ] Fetch all Pokémon in submitted team
- [ ] Aggregate type weaknesses
- [ ] Aggregate resistances and immunities
- [ ] Return structured analysis response

## Phase 4: Role and stat analysis
- [ ] Add stat summary logic
- [ ] Add simple role classification
- [ ] Detect role imbalance
- [ ] Add recommendation generation

## Phase 5: Error handling and polish
- [ ] Add global exception handling
- [ ] Improve response messages
- [ ] Clean up DTO naming
- [ ] Refactor duplicated logic

## Phase 6: Testing
- [ ] Add tests for Pokémon lookup service
- [ ] Add tests for team validation
- [ ] Add tests for type analysis logic
- [ ] Add tests for recommendation logic

## Phase 7: Submission prep
- [ ] Review architecture alignment
- [ ] Update README
- [ ] Update assumptions
- [ ] Update session log
- [ ] Final manual endpoint testing
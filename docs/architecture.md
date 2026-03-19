# Architecture

# Architecture

## Product goal
Create a Pokémon team analysis tool that helps users build and evaluate a team of Pokémon.

The tool should allow a user to:
- input up to 6 Pokémon
- view each Pokémon’s key information
- analyze the team’s type coverage and shared weaknesses
- evaluate team balance using simple stat and role heuristics
- receive simple recommendations for improvement

This project focuses on clear reasoning and useful analysis, not full competitive battle simulation.

## Scope
This project will support:
- Pokémon lookup by name
- building a team of up to 6 Pokémon
- fetching Pokémon data from PokéAPI
- displaying normalized Pokémon information
- team weakness and resistance analysis
- basic offensive or defensive coverage insights
- simple role classification
- recommendation generation based on team weaknesses or imbalance

This project will not support:
- battle simulation
- movesets
- EV/IV calculations
- held items
- abilities-based battle effects
- natures
- breeding or progression systems
- user accounts or persistence

## Tech stack
- Backend: Java 21
- Framework: Spring Boot
- Build tool: Maven
- External data source: PokéAPI
- Testing: JUnit / Spring Boot Test

No database will be used in the first version.
No frontend web app is required in the first version.
The application will expose REST endpoints and can be tested using Postman, curl, Swagger UI, or simple request payloads.

## High-level system design
The system will act as a backend analysis service.

Flow:
1. User sends Pokémon names to the backend
2. Backend fetches Pokémon data from PokéAPI
3. Backend normalizes the external API response into internal models
4. Backend analyzes the full team
5. Backend returns structured analysis and recommendations

Architecture flow:
Client input -> Controller -> Service -> External API Client -> Mapper -> Analysis Service -> Response DTO

## Main responsibilities by layer

### Controller layer
Responsible for:
- receiving HTTP requests
- validating request payloads
- returning structured API responses
- exposing endpoints for lookup and team analysis

### Service layer
Responsible for:
- coordinating Pokémon lookup
- orchestrating team analysis
- applying business logic
- generating recommendations

### External API client layer
Responsible for:
- calling PokéAPI
- handling API errors and timeouts
- returning raw external data safely to the service or mapper layer

### Mapper layer
Responsible for:
- converting PokéAPI responses into internal DTOs/models
- ensuring only required fields are exposed internally

### Analysis layer
Responsible for:
- type weakness analysis
- resistance and immunity aggregation
- stat summary calculations
- role classification
- team recommendation rules

### DTO layer
Responsible for:
- request payloads
- response payloads
- keeping API contracts explicit and stable

## Suggested package structure
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

## Core domain concepts

### Pokémon
A normalized Pokémon object should include:
- id
- name
- types
- abilities
- base stats
- sprite URL

### Team
A team is a collection of up to 6 Pokémon.

### Type analysis
The system should evaluate:
- which attacking types the team is weak to
- which attacking types the team resists well
- whether multiple team members share the same weakness
- whether the team has useful resistances or immunities

### Role analysis
The system should classify Pokémon using simple heuristics such as:
- physical attacker
- special attacker
- defensive wall
- fast attacker
- bulky support
- mixed attacker

These are heuristic labels only, not competitive-grade classifications.

### Recommendations
Recommendations should be generated from simple deterministic rules, for example:
- too many shared weaknesses
- lack of speed
- lack of defensive stability
- overly one-dimensional offense
- too many similar roles

## External data source
Primary source:
- PokéAPI

Expected Pokémon fields to use:
- name
- id
- types
- abilities
- stats
- sprites

Only fields needed for this assignment should be mapped into internal models.

## Initial API design

### Health check
GET /api/health

Response:
{
  "status": "ok"
}

### Search Pokémon by name
GET /api/pokemon/{name}

Purpose:
- fetch a single Pokémon by name
- return normalized Pokémon information

Example response:
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

### Analyze team
POST /api/team/analyze

Example request:
{
  "pokemonNames": ["pikachu", "charizard", "blastoise"]
}

Example response:
{
  "team": [
    {
      "name": "pikachu",
      "types": ["electric"]
    },
    {
      "name": "charizard",
      "types": ["fire", "flying"]
    },
    {
      "name": "blastoise",
      "types": ["water"]
    }
  ],
  "typeAnalysis": {
    "majorWeaknesses": ["rock"],
    "coveredThreats": ["grass", "steel"],
    "sharedWeaknessCounts": {
      "rock": 2
    }
  },
  "roleAnalysis": {
    "roles": {
      "pikachu": "fast attacker",
      "charizard": "special attacker",
      "blastoise": "defensive wall"
    },
    "summary": [
      "team has some speed",
      "team has limited defensive depth"
    ]
  },
  "recommendations": [
    "Consider adding a ground or fighting answer to improve rock matchup",
    "Consider adding another bulky Pokémon for defensive balance"
  ]
}

## Analysis rules

### Type weakness analysis
The first version should use simple type-effectiveness rules:
- identify whether each Pokémon is weak, resistant, immune, or neutral to each attacking type
- aggregate results across the team
- flag major weaknesses when multiple team members are weak to the same attacking type

Suggested simple rules:
- if 3 or more Pokémon are weak to one type, mark as a major weakness
- if 2 Pokémon are weak and no team member resists that type, mark as a concern
- if several Pokémon resist or are immune to a type, mark it as a team strength

### Role classification rules
The first version should use stat-based heuristics only.

Example ideas:
- physical attacker: Attack significantly higher than Special Attack
- special attacker: Special Attack significantly higher than Attack
- fast attacker: Speed above a threshold
- defensive wall: high combined HP, Defense, and Special Defense
- mixed attacker: Attack and Special Attack both reasonably high
- bulky support: moderate defenses and lower offensive emphasis

These do not need to be perfect. They need to be understandable and consistent.

### Recommendation rules
Recommendations should be deterministic and explainable.

Examples:
- too many Pokémon weak to electric
- too many physical attackers and no special attacker
- no fast Pokémon
- weak defensive balance
- too much role overlap

## Validation rules
- team must contain between 1 and 6 Pokémon
- Pokémon names should be normalized to lowercase before lookup
- duplicate Pokémon may be rejected in the first version
- invalid Pokémon names should return a clear error response

## Error handling
The system should provide clear error responses for:
- invalid Pokémon names
- empty team input
- too many Pokémon submitted
- failures from PokéAPI
- unexpected internal server errors

## Non-functional goals
- code should be modular and maintainable
- logic should be easy to explain in README
- endpoints should be easy to test manually
- external API data should be normalized before use
- business rules should stay simple and explicit

## Build priorities

### Phase 1
- project scaffolding
- health endpoint
- single Pokémon lookup endpoint
- PokéAPI client setup

### Phase 2
- team analysis endpoint
- type weakness aggregation
- normalized response DTOs

### Phase 3
- role classification
- recommendation generation
- tests
- README cleanup

## Key design decisions
- backend only for version 1
- no database
- no authentication
- no persistence
- no battle simulation
- simple rules are preferred over complex mechanics
- internal DTOs will be used instead of exposing raw PokéAPI responses

## Success criteria
A successful first version should:
- accept Pokémon input
- fetch valid Pokémon data reliably
- return useful team analysis
- explain weaknesses and role balance clearly
- produce simple but sensible recommendations
- remain small, readable, and easy to extend

# Pokemon Team Builder

Backend-only Pokemon team analysis tool using Spring Boot.

## Backend Structure

The Spring Boot backend lives under `backend/` and follows the layered architecture from `docs/architecture.md`.

- `backend/pom.xml`: Maven configuration for Java 21 and Spring Boot web and test dependencies
- `backend/src/main/java/com/example/pokemon`: application code
- `backend/src/main/java/com/example/pokemon/controller`: REST controllers
- `backend/src/main/java/com/example/pokemon/service`: business logic services
- `backend/src/main/java/com/example/pokemon/client`: PokeAPI client and raw external response models
- `backend/src/main/java/com/example/pokemon/dto`: request and response DTOs
- `backend/src/main/java/com/example/pokemon/mapper`: mappers for converting PokeAPI models into API DTOs
- `backend/src/main/java/com/example/pokemon/model`: internal analysis models and type chart
- `backend/src/main/java/com/example/pokemon/exception`: custom exceptions and global error handling
- `backend/src/main/java/com/example/pokemon/config`: Spring configuration including the `RestClient`
- `backend/src/main/resources/application.yml`: application configuration
- `backend/src/test/java/com/example/pokemon`: controller, service, and mapper tests

## Available Endpoints

- `GET /api/health`
- `GET /api/pokemon/{name}`
- `POST /api/team/analyze`

Detailed request setup and example responses are in the `Testing With Postman` section below.

## Run Locally

Prerequisites:

- Java 21 installed and available in `PATH`
- Maven 3.9+ installed and available in `PATH`

Steps:

```bash
cd backend
mvn clean test
mvn spring-boot:run
```

## Testing With Postman

Postman is the recommended tool for manual API testing during development.
- Test these endpoints in Postman:
  - `GET /api/health`
  - `GET /api/pokemon/{name}`
  - `POST /api/team/analyze`

### Health Endpoint

1. Set method to `GET`.
2. Set URL to `http://localhost:8080/api/health`.
3. Click `Send`.

Expected response:

```json
{"status":"ok"}
```

### Pokemon Lookup

1. Set method to `GET`.
2. Set URL to `http://localhost:8080/api/pokemon/pikachu`.
3. Click `Send`.

Expected response:

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

### Team Analysis

1. Set method to `POST`.
2. Set URL to `http://localhost:8080/api/team/analyze`.
3. Open the `Body` tab.
4. Select `raw`.
5. Set the body type to `JSON`.
6. Paste this request body:

```json
{
  "pokemonNames": ["pikachu", "charizard", "blastoise"]
}
```

7. Click `Send`.

Expected response shape:

```json
{
  "team": [
    { "name": "pikachu", "types": ["electric"] },
    { "name": "charizard", "types": ["fire", "flying"] },
    { "name": "blastoise", "types": ["water"] }
  ],
  "typeAnalysis": {
    "weaknesses": [
      {
        "type": "electric",
        "affectedPokemon": ["charizard", "blastoise"],
        "coveringPokemon": ["pikachu"],
        "severity": "shared weakness"
      }
    ],
    "resistances": [
      { "type": "electric", "pokemon": ["pikachu"] }
    ],
    "immunities": [
      { "type": "ground", "pokemon": ["charizard"] }
    ],
    "synergyNotes": [
      "electric pressure can be covered by pikachu"
    ]
  },
  "roleAnalysis": {
    "roles": {
      "pikachu": "bulky support",
      "charizard": "fast attacker",
      "blastoise": "bulky support"
    },
    "roleCounts": {
      "bulky support": 2,
      "fast attacker": 1
    },
    "summary": [
      "team has at least one fast attacker",
      "team has some defensive backbone",
      "team has some offensive variety"
    ]
  },
  "statSummary": {
    "totalHp": 162,
    "totalAttack": 194,
    "totalDefense": 162,
    "totalSpecialAttack": 194,
    "totalSpecialDefense": 190,
    "totalSpeed": 258,
    "averageHp": 54,
    "averageAttack": 65,
    "averageDefense": 54,
    "averageSpecialAttack": 65,
    "averageSpecialDefense": 63,
    "averageSpeed": 86,
    "strengths": [],
    "weaknesses": [
      "team is light on physical defense",
      "team is light on special defense",
      "team has low average HP",
      "team lacks strong attacking stats"
    ]
  },
  "recommendations": []
}
```

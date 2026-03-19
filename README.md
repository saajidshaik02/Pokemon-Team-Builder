# Pokemon Team Builder

Backend-only Pokemon team analysis project scaffold.

## Backend Structure

The Spring Boot backend lives under `backend/` and follows the layered package layout from `docs/architecture.md`.

- `backend/pom.xml`: Maven build for Java 21 with Spring Boot web and test dependencies
- `backend/src/main/java/com/example/pokemon/PokemonApplication.java`: Spring Boot entry point
- `backend/src/main/java/com/example/pokemon/controller`: REST controllers
- `backend/src/main/java/com/example/pokemon/service`: business logic services
- `backend/src/main/java/com/example/pokemon/client`: PokéAPI client and raw external response models
- `backend/src/main/java/com/example/pokemon/dto`: request and response DTOs
- `backend/src/main/java/com/example/pokemon/mapper`: mapping from PokéAPI models to API DTOs
- `backend/src/main/java/com/example/pokemon/model`: internal model package placeholder
- `backend/src/main/java/com/example/pokemon/exception`: exception classes and global error handling
- `backend/src/main/java/com/example/pokemon/config`: Spring configuration including the PokéAPI `RestClient`
- `backend/src/main/resources/application.yml`: application configuration
- `backend/src/test/java/com/example/pokemon`: controller, mapper, and service tests

## Available Endpoint

- `GET /api/health` returns `{"status":"ok"}`
- `GET /api/pokemon/{name}` returns normalized Pokémon data from PokéAPI

## Run Locally

Requirements:

- Java 21 installed and available on `PATH`
- Maven 3.9+ installed and available on `PATH`

Commands:

```bash
cd backend
mvn clean test
mvn spring-boot:run
```

Once the application starts, test it with:

```bash
curl http://localhost:8080/api/health
```

Expected response:

```json
{"status":"ok"}
```

Lookup example:

```bash
curl http://localhost:8080/api/pokemon/pikachu
```

Example response:

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

# CLAUDE.md

Skeleton: Kotlin REST API template using Ktor and hexagonal architecture.

## MANDATORY: Use backend-mode Skill

**BEFORE implementing, planning, or discussing architecture: invoke the `backend-mode` skill.**

This skill defines how this project works: layer placement, TDD workflow, testing strategy, and patterns. Do not guess or assume - read the skill first.

## Build Commands

```bash
./gradlew spotlessApply  # Format code (always run first)
./gradlew build          # Build
./gradlew test           # Run tests
./gradlew run            # Run application
```

## Project-Specific

### OpenAPI

Update `app/src/main/resources/static/openapi.yaml` when adding/changing handlers or DTOs.

## Tech Stack

- Kotlin 2.3.0, Java 25
- Ktor 3.3.2 (Netty)
- SQLite + JDBI
- Kotest 6.0.5 + kogiven
- Flyway migrations

## Docker

Production runs in Docker (Alpine-based). See `Dockerfile` for image definition.

## Database

- SQLite file: `skeleton.db` (in-memory for tests)
- Migrations: `app/src/main/resources/db/migration/V{version}__{description}.sql`

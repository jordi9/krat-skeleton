# Skeleton

Kotlin REST API skeleton using [krat](https://github.com/jordi9/krat) libraries. All server parts follow
Ratpack-inspired patterns, built on Ktor.

## Getting Started

```bash
./gradlew run            # Start on http://localhost:8080
./gradlew test           # Run tests
./gradlew spotlessApply  # Format code
./gradlew build          # Full build (format check + test + package)
```

## Architecture

Hexagonal architecture organized by feature:

```
feature/{name}/
├── inbound/       # (inbound to the domain) HTTP handlers & DTOs
├── application/   # Use cases
├── domain/        # Entities, value objects, exceptions
└── outbound/      # (outbound from the domain) Repositories & external clients
```

Cross-cutting concerns live in `shared/` (error handling, health checks, metrics, tracing, migrations).

Dependencies are wired using manual DI in [`Registry.kt`](app/src/main/kotlin/com/jordi9/skeleton/Registry.kt), a simple
container that's easy to substitute in tests. I used to love Guice and tried all the DI libraries for Kotlin, but I
ended up not liking any.

## Database

- SQLite file: `skeleton.db` (temp file per test suite)
- Migrations using flyway: `app/src/main/resources/db/migration/V{version}__{description}.sql`

## Configuration

Ktor's HOCON config in `application.conf`. Override locally with `application-local.conf` (gitignored).

## Docker

```bash
./gradlew build
docker build -t skeleton .
docker run -p 8080:8080 skeleton
```

Alpine-based image using Amazon Corretto 25. See [`Dockerfile`](Dockerfile).

## Testing

Follows the [GOOS](http://www.growing-object-oriented-software.com/) testing style (Nat Pryce & Steve Freeman): tests
drive the design from the outside in, starting with acceptance tests that exercise the full stack and pushing down to
unit tests as needed.

Acceptance tests use [kogiven](https://github.com/jordi9/krat/tree/main/krat-kogiven) for BDD-style Given-When-Then
scenarios:

```kotlin
"create an item" {
  Given.`no items exist`()
  When.`creating an item`("Buy milk", "From the store")
  Then.`the item was created`()
    .and().`the item has name`("Buy milk")
    .and().`a notification was sent`("Item created: Buy milk")
}
```

Tests run against a full Ktor TestApplication with a temporary SQLite database — no mocked HTTP layer. External
collaborators (e.g. notification client) are replaced with stubs to verify interactions at the boundary.

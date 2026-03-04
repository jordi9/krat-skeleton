# Skeleton

Kotlin REST API template using Ktor and hexagonal architecture.

## Tech Stack

- Kotlin, Java 25 (Corretto)
- Ktor (Netty)
- SQLite + JDBI
- Flyway migrations
- Kotest + kogiven (BDD)
- OpenTelemetry + Micrometer
- Spotless (ktlint)

## Getting Started

```bash
./gradlew run            # Start on http://localhost:8080
./gradlew test           # Run tests
./gradlew spotlessApply  # Format code
./gradlew build          # Full build (format check + test + package)
```

## API

| Method | Path | Description |
|--------|------|-------------|
| GET | `/hello` | Greeting (optional `?name=`) |
| GET | `/api/v1/items` | List items |
| POST | `/api/v1/items` | Create item |
| GET | `/api/v1/items/{id}` | Get item by ID |
| GET | `/health/liveness` | Liveness probe |
| GET | `/health/readiness` | Readiness probe |
| GET | `/metrics` | Prometheus metrics |

Full spec: [`app/src/main/resources/static/openapi.yaml`](app/src/main/resources/static/openapi.yaml)

## Architecture

Hexagonal architecture organized by feature:

```
feature/{name}/
├── inbound/       # HTTP handlers & DTOs
├── application/   # Use cases
├── domain/        # Entities, value objects, exceptions
└── outbound/      # Repositories & external clients
```

Cross-cutting concerns live in `shared/` (error handling, health checks, metrics, tracing, migrations).

Dependencies are wired in [`Registry.kt`](app/src/main/kotlin/com/jordi9/skeleton/Registry.kt) — a simple container that's easy to substitute in tests.

## Database

- SQLite file: `skeleton.db` (temp file per test suite)
- Migrations: `app/src/main/resources/db/migration/V{version}__{description}.sql`

## Configuration

HOCON config in `application.conf`. Override locally with `application-local.conf` (gitignored).

Key settings: port (`PORT` env var), database URL, tracing (OTLP endpoint), CORS.

## Docker

```bash
./gradlew build
docker build -t skeleton .
docker run -p 8080:8080 skeleton
```

Alpine-based image using Amazon Corretto 25. See [`Dockerfile`](Dockerfile).

## Testing

Follows the [GOOS](http://www.growing-object-oriented-software.com/) testing style (Nat Pryce & Steve Freeman): tests drive the design from the outside in, starting with acceptance tests that exercise the full stack and pushing down to unit tests as needed.

Integration tests use kogiven for BDD-style Given-When-Then scenarios:

```kotlin
"create an item" {
  Given.`no items exist`()
  When.`creating an item`("Buy milk", "From the store")
  Then.`the item was created`()
    .and().`the item has name`("Buy milk")
    .and().`a notification was sent`("Item created: Buy milk")
}
```

Tests run against a full Ktor TestApplication with a temporary SQLite database — no mocked HTTP layer. External collaborators (e.g. notification client) are replaced with stubs to verify interactions at the boundary.

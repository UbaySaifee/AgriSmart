# AgriSmart

AgriSmart is a compact farm decision-support platform built for a college hackathon demo. It connects a farmer's farm, soil, crop, and live local weather data in one clear dashboard, then produces transparent rule-based advisories.

## Problem

Farm information is often scattered across notebooks, weather apps, and memory. That makes it hard to build a quick picture of current field conditions or explain why an action may deserve attention.

## Solution

AgriSmart gives a farmer one end-to-end flow:

1. Register or sign in securely.
2. Create a farm with its location.
3. Record soil measurements and the current crop.
4. View live Open-Meteo weather for the farm coordinates.
5. Review explainable prototype advisories and the reason behind each one.

The advisory engine is deliberately rule-based. It does not claim to be machine learning or a certified agronomic prescription.

## MVP Features

- JWT registration and login with BCrypt password hashing
- Current farmer account and farmer profile
- Ownership-protected farm create, list, detail, update, and soft delete APIs
- Soil profile create/update and read
- Crop catalog and farm crop records
- Live Open-Meteo current and daily weather
- Weather history stored in PostgreSQL
- Explainable irrigation, soil, weather, crop, and general advisory cards
- Aggregate dashboard API
- Responsive React dashboard with loading, empty, error, and success states
- Centralized, client-safe API errors and validation messages
- Swagger/OpenAPI documentation

## Architecture

```text
React + TypeScript + Vite
        │ JWT / REST
        ▼
Spring Boot controllers
        ▼
Services and rule-based recommendation engine
        ├── JPA repositories ── PostgreSQL
        └── Weather integration ── Open-Meteo
```

Backend packages follow `com.agrismart.backend` with `controller`, `dto`, `entity`, `repository`, `service`, `service.impl`, `mapper`, `security`, `config`, `exception`, and `integration` layers.

## Technology Stack

- Java 17, Spring Boot 4, Spring Web MVC
- Spring Data JPA, Spring Security, OAuth2 Resource Server JWT
- PostgreSQL 16
- React 19, TypeScript, Vite, Tailwind CSS, React Router, Axios, Lucide React
- Maven, npm, Docker Compose, and Nginx

## Prerequisites

- Java 17+
- Maven 3.9+
- Node.js 20+
- npm
- PostgreSQL 16+, or Docker with Docker Compose

## Environment Variables

Copy `.env.example` to a local `.env` if desired. `.env` is ignored by Git.

| Variable | Required | Purpose |
| --- | --- | --- |
| `DB_URL` | No | JDBC URL; defaults to `jdbc:postgresql://localhost:5432/agrismart` |
| `DB_USERNAME` | No | PostgreSQL username; defaults to `postgres` |
| `DB_PASSWORD` | Yes | PostgreSQL password |
| `JWT_SECRET` | Yes | Random HMAC secret of at least 32 characters |
| `SERVER_PORT` | No | Backend port; defaults to collision-safe `18082` |
| `FRONTEND_URL` | No | Allowed CORS origin; defaults to `http://localhost:5174` |
| `SEED_CROP_CATALOG` | No | Seeds Soybean, Wheat, and Cotton when absent; defaults to `true` |
| `VITE_API_URL` | No | Frontend API root; defaults to same-origin `/api` |
| `VITE_DEV_API_PROXY_TARGET` | No | Vite proxy target; defaults to `http://127.0.0.1:18082` |
| `VITE_PORT` | No | Vite port; defaults to collision-safe `5174` |

Never commit real passwords, JWT secrets, or API keys.

## Database Setup

### Docker option

Set a local database password in your shell, then start PostgreSQL:

```bash
export DB_PASSWORD='choose-a-local-password'
docker compose up -d db --wait
```

The included Compose file exposes PostgreSQL on port `5435` by default. Set `DB_PORT` to change it.

### Existing PostgreSQL option

```sql
CREATE DATABASE agrismart;
```

Then point `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` at that database. Hibernate creates or updates the MVP schema.

## Run the Backend

```bash
cd backend
export DB_URL='jdbc:postgresql://localhost:5435/agrismart'
export DB_USERNAME='postgres'
export DB_PASSWORD='your-local-database-password'
export JWT_SECRET='a-random-secret-with-at-least-32-characters'
mvn spring-boot:run
```

Backend URL: `http://localhost:18082`

Health check: `http://localhost:18082/api/health`

Swagger UI: `http://localhost:18082/swagger-ui/index.html`

OpenAPI JSON: `http://localhost:18082/v3/api-docs`

## Run the Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend URL: `http://localhost:5174`

Vite proxies `/api` to the AgriSmart backend on `18082`. These explicit defaults prevent an unrelated app on `5173` or `8080` from receiving AgriSmart authentication requests.

The JWT and minimal current-user summary are stored in browser local storage so authenticated navigation survives a refresh. Logging out removes both values.

## Build and Test

Backend compilation:

```bash
cd backend
mvn clean package -DskipTests
```

Backend integration tests use an isolated H2 database and a controlled weather client:

```bash
cd backend
mvn test
```

Frontend production build:

```bash
cd frontend
npm run build
```

With a running backend, the smoke script checks health, registration, login, JWT protection, farm ownership, soil, crop, live weather, recommendations, and dashboard data:

```bash
API_BASE_URL='http://localhost:18082/api' \
E2E_PASSWORD='a-disposable-test-password' \
./scripts/e2e-smoke.sh
```

## Hackathon Deployment

The deployment stack builds the React app into Nginx, proxies `/api` to Spring Boot on the private Docker network, and keeps PostgreSQL private. Only one browser-facing port is exposed, so CORS and development-port collisions do not affect the deployed app.

```bash
cp .env.deploy.example .env.deploy
# Replace both placeholder secrets in .env.deploy. Generate a JWT secret with:
openssl rand -base64 48
docker compose -p agrismart-hackathon --env-file .env.deploy -f compose.deploy.yaml up -d --build --wait
```

Open `http://localhost:8088`. Registration, login, dashboards, API requests, and SPA refreshes all use this single origin.

Verify the running stack with disposable credentials:

```bash
API_BASE_URL='http://localhost:8088/api' \
E2E_PASSWORD='a-disposable-test-password' \
./scripts/e2e-smoke.sh
```

The stack uses named storage and `restart: unless-stopped`. Stop it without deleting data:

```bash
docker compose -p agrismart-hackathon --env-file .env.deploy -f compose.deploy.yaml down
```

For a public demo, deploy the same Compose stack on a VM or container host, set `FRONTEND_URL` to the HTTPS site origin, and place TLS in front of port 8088. Do not commit `.env.deploy`.

## Demo Credentials

No fixed credentials or passwords are stored in the repository. Register a disposable farmer account from the UI before the demo, or create one live during the presentation.

## Hackathon Demo Flow

1. Register as a farmer and sign in.
2. Add **Rahul Smart Farm** in Khandwa with valid coordinates.
3. Record black soil, pH, nutrients, and moisture.
4. Link Soybean as the current Kharif crop.
5. Return to the dashboard to show the connected farm overview.
6. Point out the `LIVE` Open-Meteo source on the weather card.
7. Open Smart Advisories and explain the visible reason for each rule.
8. Emphasize the disclaimer: critical decisions should be verified with local agricultural experts.

Core story: instead of showing farmers disconnected information, AgriSmart combines farm, soil, crop, and weather information into one practical decision-support dashboard.


## Exact Three-Minute Presenter Script

- **0:00–0:20 — Login:** “AgriSmart is a unified decision-support platform for farmers.” Sign in with a disposable demo account.
- **0:20–0:45 — Dashboard:** “The platform connects the farmer's farm, crop, soil, and live weather data.” Select the demo farm if more than one exists.
- **0:45–1:15 — Farm context:** Point to the farm area, location, current crop, soil pH, and moisture summary cards.
- **1:15–1:40 — Live weather:** “Weather is fetched from Open-Meteo using the farm's saved coordinates.” Show the live source and observation time.
- **1:40–2:15 — Soil and crop:** Open Soil Health and Current Crop to show that readings and crop records belong to the selected farm.
- **2:15–2:45 — Smart advisories:** Filter the advisories, open one card, and explain its reason field. State that the engine uses transparent prototype rules.
- **2:45–3:00 — Close:** “AgriSmart converts disconnected farm information into simple next actions. Future integrations can add validated crop models, sensors, disease detection, and regional-language access.”

## Known Limitations

- Advisory thresholds are demonstration heuristics, not certified agronomic prescriptions.
- Live weather requires valid farm coordinates and an available Open-Meteo connection.
- No fixed credentials are committed; prepare a disposable account and farm before judging begins.
- The prototype stores JWT authentication in browser local storage; a production release should use a hardened session strategy.
- Production database migrations and monitoring remain future work; public hosting and TLS still require a chosen deployment provider.
## API Summary

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET|PUT /api/users/me`
- `GET|PUT /api/farmer/profile`
- `POST|GET /api/farms`
- `GET|PUT|DELETE /api/farms/{farmId}`
- `GET|PUT /api/farms/{farmId}/soil`
- `GET /api/crops`
- `POST|GET /api/crops/farms/{farmId}`
- `GET /api/farms/{farmId}/weather/current`
- `GET /api/farms/{farmId}/weather/history`
- `GET /api/farms/{farmId}/recommendations`
- `GET /api/dashboard?farmId={optionalFarmId}`

All farm-specific services verify ownership using the authenticated JWT subject.

## Advisory Safety

Prototype thresholds are documented in `RecommendationServiceImpl`. Agricultural thresholds vary by crop, soil, and region; these rules are demonstration heuristics only. The UI and API both carry the local-expert disclaimer.

## Future Scope

- Region- and crop-specific advisory rules reviewed by agronomists
- Multilingual and voice-first access
- Optional IoT soil-sensor ingestion
- Satellite or disease-imagery integrations after validation
- Government scheme discovery and market information
- Production database migrations, monitoring, and deployment automation

# Product Catalog

A full-stack product catalog management application built as a coding exercise.

## Tech Stack

**Backend:** Java 21, Spring Boot 4.0.9, H2 Database, Gradle  
**Frontend:** React 19, TypeScript, Vite, Axios

## Prerequisites

- Java 21+
- Node.js 22+
- npm
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) for containerized setup

## Run with Docker

```bash
git clone https://github.com/grgks/product-catalog.git
cd product-catalog
docker-compose up --build
```

- **Frontend:** http://localhost:4000 (if port 4000 is in use, change it in `docker-compose.yml` under `frontend > ports`)
- **Backend API:** http://localhost:8080/api/products

```bash
docker-compose down      # stop
docker-compose down -v   # stop + remove data
```

## Run Locally

### Backend

```bash
./gradlew bootRun
```

Backend runs at `http://localhost:8080`

### Frontend



```bash
cd frontend
npm install
npm run dev
```
> ***Note:*** When running locally, update `frontend/src/api/api.ts --> (line: 5) <--` to point to the backend directly:
> ```typescript
> baseURL: "http://localhost:8080/api/products"
> ```
> The Docker setup uses `/api/products` because Nginx proxies the requests.

Frontend runs at `http://localhost:5173`

## Run Tests

```bash
./gradlew test
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/products | Search/filter products |
| GET | /api/products/{id} | Get product by ID |
| POST | /api/products | Create product |
| PUT | /api/products/{id} | Update product |
| DELETE | /api/products/{id} | Delete product |

**Query parameters:** `?search=`, `?category=`, `?available=`, `?sku=`

## Screenshots

### API (Postman)

**POST /api/products ➡️ 201 Created**

<img src="docs/Create-product.png" width="600">

**GET /api/products/{id} ➡️ 200 OK**

<img src="docs/Get-product-by-id.png" width="600">

**PUT /api/products/{id} ➡️ 200 OK**

<img src="docs/Update(PUT)-by-id.png" width="600">

**DELETE /api/products/{id} ➡️ 200 OK**

<img src="docs/Delete-by-id.png" width="600">

**GET /api/products?category= ➡️ 200 OK**

<img src="docs/Search-by-category.png" width="600">

**POST /api/products (missing name) ➡️ 400 Bad Request**

<img src="docs/Bad-Request-400.png" width="600">

**GET /api/products/{id} (not found) ➡️ 404 Not Found**

<img src="docs/Not-Found-404.png" width="600">

**POST /api/products (duplicate name) ➡️ 409 Conflict**

<img src="docs/Already-exists-Conflict409.png" width="600">

### Frontend

**Product List-Search Bar**

<img src="docs/Frontend-Product-list.png" width="600">

**New Product Form (insert product)**

<img src="docs/Frontend-insert-product-form.png" width="600">

## Technical Notes

- Single `@Query` with optional parameters for combined search/filter
- Record-based DTOs with Jakarta validation
- Centralized error handling via `@RestControllerAdvice`
- UUID-based SKU auto-generation
- CORS configured for frontend dev server
- Integration tests with `@SpringBootTest` and `MockMvc`
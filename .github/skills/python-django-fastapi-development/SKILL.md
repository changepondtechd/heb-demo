---
name: python-django-fastapi-development
description: HEB Python backend standards for Django and FastAPI services in the Digital Application.
---

# HEB Python/Django/FastAPI Development Skill

## Standards

- Follow the repository's existing Python structure and dependency management.
- Determine whether Django, FastAPI, or shared modules own the behavior.
- Reuse existing services, schemas, dependencies, and middleware.
- Keep API validation explicit.
- Follow existing sync/async conventions.
- Avoid unnecessary framework mixing.
- Keep business logic out of thin transport layers where the project uses service abstractions.
- Use type hints where consistent with the repository.

## Database

- Use PostgreSQL-compatible patterns.
- Watch for N+1 queries.
- Use transactions for multi-step operations that must be atomic.
- Avoid exposing internal models or sensitive columns through APIs.

## Testing

Use the repository's established testing framework and fixtures.
Cover success, validation, authorization, exceptions, and boundary conditions.

---
name: testing
description: Reusable HEB testing guidance for React, Java/Spring Boot, Python, FastAPI, Django, GraphQL, and APIs.
---

# HEB Testing Skill

## Test Strategy

Prefer the smallest test level that proves the behavior:
- Unit tests for isolated business rules
- Component tests for React behavior
- API tests for contracts and validation
- Integration tests for persistence/integration boundaries
- End-to-end tests only where they provide unique value

## Required Scenarios

For meaningful changes, consider:
- Happy path
- Validation failure
- Authorization failure
- Exception/error handling
- Boundary values
- Regression scenario

## Rules

- Reuse existing fixtures and test utilities.
- Avoid brittle implementation-detail assertions.
- Keep tests deterministic.
- Do not disable validation simply to make tests pass.
- Do not add production-only test hooks without justification.

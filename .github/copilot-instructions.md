# HEB GitHub Copilot Instructions

## Project Context

HEB contains two applications:

### Digital Client Portal
- Frontend: React
- Backend: Java 21, Spring Boot
- Database: SQL Server
- Platform: Azure AKS
- Deployment: Flux
- CI/CD: GitHub Actions

### Digital Application
- Frontend: React
- Backend: Python, Django, FastAPI, GraphQL
- Database: PostgreSQL
- Frontend deployment: Vercel
- Backend deployment: Azure AKS
- Deployment: Flux

## General Engineering Rules

1. Understand the existing architecture before changing code.
2. Prefer existing project patterns, utilities, libraries, and abstractions.
3. Keep changes focused and avoid unrelated refactoring.
4. Do not introduce a dependency when an existing dependency can solve the problem.
5. Never hard-code credentials, tokens, connection strings, private keys, or secrets.
6. Treat API contracts and database schemas as controlled interfaces.
7. Preserve backward compatibility unless a breaking change is explicitly requested.
8. Add or update automated tests for changed behavior.
9. Validate error handling, input validation, logging, and security for new functionality.
10. Do not make destructive database or infrastructure changes without explicit approval.
11. Do not bypass CI/CD, GitOps, or repository governance controls.
12. Explain important assumptions and architectural trade-offs before making high-impact changes.
13. Prefer small, reviewable commits and pull requests.

## AI Working Rules

- Start by identifying the requirement and acceptance criteria.
- Inspect relevant files before proposing implementation.
- Separate planning from implementation when the task is complex.
- Reuse relevant HEB skills instead of duplicating technology guidance.
- For cross-application work, clearly identify which application is affected.
- Never assume that a similar implementation in the other application is identical.
- When uncertain, state the uncertainty and identify what should be verified.

## Definition of Done

A change is considered ready only when:
- Implementation follows the applicable application and technology standards.
- Tests are added or updated.
- Security considerations have been checked.
- Relevant documentation is updated.
- Build/lint/test validation is identified or performed.
- Deployment impact is understood.
- The final summary identifies files changed, tests run, and remaining risks.

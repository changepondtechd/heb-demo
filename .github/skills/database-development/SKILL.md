---
name: database-development
description: HEB database design, query, schema, and migration guidance for SQL Server and PostgreSQL.
---

# HEB Database Development Skill

## Databases

Digital Client Portal:
- SQL Server

Digital Application:
- PostgreSQL

## Standards

- Inspect existing schema, constraints, indexes, and access patterns before changes.
- Prefer parameterized queries and ORM/query-builder conventions already used.
- Protect data integrity with appropriate constraints.
- Consider indexing and query plans for high-volume operations.
- Avoid destructive changes unless explicitly approved.
- Plan schema changes for backward compatibility and deployment order.
- Never store secrets in database scripts.
- Do not expose sensitive data unnecessarily.

## Change Checklist

1. Existing schema reviewed
2. Backward compatibility assessed
3. Migration/versioning identified
4. Index impact considered
5. Data integrity considered
6. Rollback strategy identified
7. Tests updated

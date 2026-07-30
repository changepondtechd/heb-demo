---
name: spring-boot-development
description: HEB Java 17 and Spring Boot development standards for the Digital Client Portal.
---

# HEB Spring Boot Development Skill

## Stack

Java 21, Spring Boot, Spring Data JPA where applicable, SQL Server, REST APIs, JUnit, Mockito.

## Standards

- Follow existing package and layering conventions.
- Use constructor injection.
- Keep controllers focused on HTTP/API concerns.
- Keep business rules in services.
- Keep persistence logic in repositories/data-access components.
- Use DTOs according to existing API conventions.
- Validate request input.
- Use consistent exception handling.
- Preserve API compatibility.
- Avoid unnecessary dependencies.
- Consider transaction boundaries and query performance.

## SQL Server

- Review existing schema and indexes before changing queries.
- Avoid destructive schema operations without explicit approval.
- Use parameterized access.
- Identify migration/versioning requirements for schema changes.

## Testing

Cover normal behavior, validation failures, exceptions, and important business rules.

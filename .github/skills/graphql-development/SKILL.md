---
name: graphql-development
description: HEB GraphQL development and review standards for the Digital Application.
---

# HEB GraphQL Development Skill

## Standards

- Inspect the existing schema before adding types or fields.
- Preserve backward compatibility when possible.
- Define clear types and nullability.
- Validate authorization at resolver/service boundaries.
- Avoid exposing internal or sensitive database fields.
- Prevent N+1 access patterns using established batching/data-loader patterns when available.
- Consider query depth, complexity, pagination, and resource consumption.
- Keep business rules outside resolvers when the architecture provides service layers.

## Testing

Test:
- Schema behavior
- Resolver success paths
- Validation
- Authorization failures
- Missing/invalid inputs
- Error behavior
- Sensitive field protection

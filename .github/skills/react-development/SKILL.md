---
name: react-development
description: HEB React development standards for the Digital Client Portal and Digital Application. Use when creating, modifying, debugging, or reviewing React code.
---

# HEB React Development Skill

## Standards

- Inspect existing component and folder conventions first.
- Reuse existing components, hooks, API clients, and design-system components.
- Keep components focused and composable.
- Keep business authorization decisions on the backend.
- Handle loading, empty, error, and success states.
- Validate user input consistently with existing project patterns.
- Avoid unnecessary global state.
- Avoid duplicate API requests.
- Keep secrets and privileged configuration out of browser code.
- Follow existing routing and state-management conventions.
- Preserve accessibility and keyboard navigation.

## Testing

Add or update tests for:
- Component behavior
- User interactions
- Validation
- Error states
- API integration behavior where appropriate

## Review Checklist

- No secret exposure
- No unnecessary re-rendering
- No unsafe HTML rendering
- Accessible controls
- Responsive behavior preserved
- Existing patterns reused

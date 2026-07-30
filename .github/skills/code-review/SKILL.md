---
name: code-review
description: Reusable HEB code review checklist for correctness, security, testing, maintainability, performance, and operations.
---

# HEB Code Review Skill

## Review Order

1. Correctness
2. Security
3. Data integrity
4. API compatibility
5. Tests
6. Performance
7. Maintainability
8. Observability
9. Deployment risk

## Reviewer Behavior

- Review the diff first.
- Prioritize defects over style.
- Avoid unrelated refactoring requests.
- Explain why a finding matters.
- Suggest practical remediation.
- Mark severity clearly.
- Confirm whether tests validate the changed behavior.

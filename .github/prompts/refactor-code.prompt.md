---
description: Safely refactor HEB code while preserving behavior.
---

# Refactor HEB Code

Before changing code:
1. Explain the current pattern.
2. Identify the refactoring goal.
3. Identify behavior that must remain unchanged.
4. Identify tests that protect the behavior.

Then:
- Make the smallest safe refactoring.
- Avoid changing APIs unless explicitly requested.
- Avoid unrelated formatting or modernization.
- Update tests only when required by the refactoring.

Refactoring request:
$ARGUMENTS

---
name: security
description: Reusable HEB application and deployment security guidance. Use during development, testing, code review, API design, and DevOps changes.
---

# HEB Security Skill

## Core Principles

- Least privilege
- Defense in depth
- Secure defaults
- Input validation
- Backend authorization
- Secret management
- Minimal sensitive-data exposure

## Application Checks

- Authentication and authorization
- Injection
- XSS/unsafe rendering
- CSRF/CORS where applicable
- Sensitive data exposure
- Dependency risks
- Logging of confidential information
- API abuse/rate/resource controls

## GraphQL

Check field authorization, sensitive field exposure, query complexity/depth, pagination, and resolver data access.

## Kubernetes

Check secrets, service exposure, privileged containers, RBAC, network exposure, and configuration.

Never create, expose, or reproduce real credentials.

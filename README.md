# HEB GitHub Copilot Customization Starter Kit

This starter kit implements the HEB target model:

**Instructions → Agents → Skills → Prompts → Tools/MCP → GitHub/CI-CD**

## Applications

### Digital Client Portal
- React
- Java 17 / Spring Boot
- SQL Server
- Azure AKS
- Flux
- GitHub Actions

### Digital Application
- React
- Python / Django / FastAPI
- GraphQL
- PostgreSQL
- Frontend: Vercel
- Backend: Azure AKS / Flux

## Directory Structure

```text
.github/
├── copilot-instructions.md
├── agents/
├── skills/
└── prompts/
```

## How to use

1. Copy `.github` into the target repository.
2. Review and tailor the global instructions.
3. Review technology-specific skills against the actual codebase.
4. Start with the Planning Agent for complex requirements.
5. Use specialized agents for implementation and review.
6. Use prompt files for repeatable developer workflows.
7. Add MCP/tools later only when there is a clear use case and governance approval.

## Recommended rollout

### Phase 1
- Global instructions
- Planning Agent
- Frontend Agent
- Backend Java Agent
- Backend Python Agent
- Testing Agent
- Code Review Agent

### Phase 2
- Security Agent
- DevOps Agent
- Skills refinement based on real developer feedback

### Phase 3
- MCP integrations
- Coding Agent workflows
- GitHub Issues → Agent → PR automation

## Important

These files are a starting point. They should be validated against the actual HEB repositories, build commands, test frameworks, branching strategy, deployment manifests, Flux configuration, and security policies before being treated as authoritative engineering standards.

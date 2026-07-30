---
name: flux-github-actions
description: HEB CI/CD and GitOps guidance for GitHub Actions, container images, AKS, and Flux.
---

# HEB Flux and GitHub Actions Skill

## Deployment Flow

Source commit
→ GitHub Actions
→ Build/test
→ Container image
→ Registry
→ Git/GitOps configuration
→ Flux reconciliation
→ AKS workload

## Rules

- Treat Git as the source of truth.
- Inspect existing workflow and Flux conventions before changes.
- Verify image repository and tag strategy.
- Avoid manual AKS changes for Git-managed resources.
- Keep credentials out of workflow files.
- Prefer OIDC or existing secure authentication patterns where configured.
- Validate workflow YAML and Kubernetes manifests.
- Identify rollback behavior for deployment changes.

## Troubleshooting

Trace the deployment from:
1. Commit
2. Workflow run
3. Image build/push
4. GitOps configuration
5. Flux reconciliation
6. Kubernetes Deployment
7. Pod/image state

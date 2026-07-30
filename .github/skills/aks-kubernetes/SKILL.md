---
name: aks-kubernetes
description: HEB Azure AKS and Kubernetes deployment guidance. Use for manifests, deployments, services, configuration, scaling, and troubleshooting.
---

# HEB AKS/Kubernetes Skill

## Standards

- Follow existing namespace and resource naming conventions.
- Preserve environment separation.
- Use Kubernetes-native configuration patterns already established.
- Never hard-code secrets in manifests.
- Use resource requests/limits when the application standard requires them.
- Consider readiness/liveness/startup probes.
- Review service exposure and network boundaries.
- Keep manifests compatible with the existing Flux/Kustomize structure.

## Troubleshooting

Check:
1. Namespace
2. Deployment status
3. Pod events
4. Container logs
5. Image reference
6. Config/Secret references
7. Service/endpoints
8. Flux reconciliation state
9. Resource constraints

Prefer GitOps changes over manual production edits.

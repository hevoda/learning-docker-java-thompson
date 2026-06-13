# learning-docker-java-thompson

This repository contains a Spring Boot example project.

Continuous dependency maintenance and security scanning added:

- Dependabot configured to check Maven dependencies weekly (.github/dependabot.yml)
- CI: OWASP Dependency-Check added to the Maven CI workflow (.github/workflows/maven-ci.yml)
- Automated dependency updates workflow (.github/workflows/ci-dependency-updates.yml)

Workflows summary:

- On push / PR: run Maven build and OWASP Dependency-Check (maven-ci.yml)
- Weekly: Dependabot opens direct dependency updates PRs
- Weekly schedule: ci-dependency-updates.yml runs the Maven Versions Plugin and opens a PR with updated direct dependency versions

Review CI results and dependency-update PRs before merging.

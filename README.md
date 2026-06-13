
# learning-docker-java-thompson

Questo repository contiene un progetto esempio Spring Boot.

Continuous integration, quality scans e manutenzione delle dipendenze:

- `Dependabot` configurato per controllare le dipendenze Maven settimanalmente (`.github/dependabot.yml`).
- CI: GitHub Actions esegue la build Maven con il workflow `./github/workflows/maven-ci.yml` (usa `./mvnw clean package`).
- Analisi della qualità del codice con Qodana tramite il workflow ` .github/workflows/qodana-code-quality.yml`.

Riepilogo dei workflow attivi:

- On push / PR: esegue la build Maven (vedi ` .github/workflows/maven-ci.yml`).
- Qodana: esegue scansioni di code quality su PR e su push verso branch di feature (vedi ` .github/workflows/qodana-code-quality.yml`).
- Weekly: `Dependabot` apre PR per aggiornamenti delle dipendenze dirette (configurato in ` .github/dependabot.yml`).

Note importanti:

- Il workflow `ci-dependency-updates.yml` per aggiornamenti automatici delle dipendenze non è più presente in questo repository; gli aggiornamenti sono gestiti da `Dependabot`.
- Il `pom.xml` include il plugin OWASP Dependency-Check per scansioni locali, ma al momento non viene eseguito come step nel workflow CI principale.

Revisiona i risultati del CI e le PR di aggiornamento delle dipendenze prima di effettuare il merge.

# QA Engineer (Manual + Automation, 7–9 Years) — Interview Prep
## CI/CD, Git, Maven & Jenkins — Questions & Answers

At this experience level, interviewers expect you to talk about **ownership, architecture decisions, troubleshooting, and best practices** — 
	not textbook definitions. 
Each answer below is written the way a senior candidate should speak: concise, with reasoning and a real-world angle.

---

## SECTION 1: ***CI/CD Concepts (Foundational + Strategic)***


### Q1. What is CI/CD, and how have you used it in your automation strategy?
**A:** CI (Continuous Integration) is the practice of frequently merging code/test changes into a shared repo, 
	with automated builds and tests validating each change. CD (Continuous Delivery/Deployment) extends this by automatically preparing 
	(or deploying) validated builds to staging/production.

In my role, I've owned the **test automation layer of the pipeline** — triggering regression/smoke suites on every merge, 
	gating deployments based on pass/fail thresholds, and publishing results (Allure/Extent reports) back to the team via 

### Q2. How do you decide what tests run at each pipeline stage?
**A:** I follow a ** risk-based gating** approach:
A risk-based gating approach is a software delivery strategy where you use specific automated tests as "gates" to block or allow code progression 
- 1.**Build/Smoke stage:** critical-path API/UI smoke tests (5–10 min) — gate for merge/deploy to QA.
	- The Goal: Catch critical flaws immediately before wasting time or disrupting other teams.
	- The Gate Content (Critical-path API/UI): You only test core features that must work for the business to function 
		(e.g., user login, adding items to a cart, database connectivity)
	- The Outcome (Merge/Deploy to QA): If these critical tests pass, the risk of the build being "dead on arrival" is low. 
	- It is safe to merge and deploy to the QA environment for deeper testing.
	
- 2.**Regression stage:** full automation suite, run nightly or pre-release.

	- Pre-Release / Release Candidates: Run immediately before deploying code to production or creating a release candidate. 
	This acts as the final safety net.
	- Post-Merge to Main Branches: Triggered automatically after code is merged into the primary development branch (e.g., main, master, or develop).
	- Bi-Sprints: it is executed at the end of a sprint 
	- On-Demand (High-Risk Changes): Triggered manually by a QA lead or developer after major 
		1. Infrastructure updates, database migrations
		2. Any major project functionality closure before the release if it completes

	
This avoids blocking developers with slow feedback while still catching regressions before release.

### Q3. What CI/CD tools have you worked with besides Jenkins?
**A:** Jenkins is my primary tool **GitLab CI/CD **.
	Concepts are largely transferable — pipeline-as-code, stages, artifacts, triggers, secrets management — the syntax differs 
	(Jenkinsfile vs `.gitlab-ci.yml` vs YAML workflows).

### Q4. How do you handle flaky tests in a CI/CD pipeline?
**A:**
1. Identify flaky tests via historical pass/fail trend reports (Jenkins test trend graphs).
2. Separate together in single suite so they don't block the pipeline, but still track them.
3. Will be checking RCA (Root-cause common issues): 
	- synchronization/wait issues, 
	- Test data collisions, 
	- Environment instability, 
	- Parallel-execution race conditions.
	
4. Apply fixes making changes accordingly as : 
	- explicit waits over `Thread.sleep`, 
	- Unique/dynamic test data per run, 
	- Retry logic **only** as a stopgap (via TestNG/JUnit retry analyzer) — 
		which is not a permanent fix but to reverify logs after multiple round failure .

### Q6. What metrics do you track/report from the pipeline as a QA lead/senior QA?
**A:** Rest pass %, automation coverage %, average pipeline execution time, defect leakage to production, 
		and mean time to detect/fix a broken build.

------------------------------------------------------------------------------------------------------------------------------------------------

## SECTION 2: Git Repository

### Q7. What branching strategy have you used, and why?
**A:** I've worked extensively with **Git Flow** and **trunk-based development with feature branches**.
-QA Team 	**Git Flow** (develop/feature/release/hotfix/master) works well for scheduled releases with QA sign-off cycles.

-Dev Team	**Trunk-based with short-lived feature branches + PRs** works better for teams doing continuous deployment, 
			since it avoids long-lived merge conflicts.

For test automation repos specifically, I usually keep a **separate branch aligned with the release branch**, 
	so automation scripts stay in sync with the app version under test.


Key Branches in Git Flow:
	1.Main Branch (main): Contains production-ready code.
	2.Release Branches (release/*): Created from develop when preparing for a release. These branches allow final testing and bug fixes before merging into main.
	3.Develop Branch (develop): The primary integration branch where features are merged before an official release.
	4.Feature Branches (feature/*): Temporary branches created from develop for implementing new features. Once completed, they are merged back into develop.
	
Not using the Hotfix Branches 	
	5.Hotfix Branches (hotfix/*): Used for critical production issues. These branches are created from main, fixed, and merged back into both main and develop.


### Q8. How do you manage automation code in Git alongside application code?
**A:** Two common patterns I've used:
1. **Separate repository** for the automation framework — cleaner separation of ownership, own CI pipeline, own versioning/tags.
I prefer separate repos for large frameworks (better modularity, independent releases).

### Q9. Explain `git rebase` vs `git merge`. When do you use each?
**A:**
- `merge` creates a new commit combining two histories — preserves full history, safe for shared/public branches.
- `rebase` rewrites commit history by replaying commits on top of another base — gives a **linear, cleaner history**, 
		but should never be done on shared/public branches (rewrites history, breaks others' clones).

I use rebase to clean up my local feature branch before raising a PR (`git rebase -i` to squash WIP commits), 
	and merge for integrating into shared branches like `develop`/`main`.

### Q10. How do you resolve merge conflicts in test automation code (e.g., Page Object files, TestNG XML)?
**A:** 
- First will understand both changes' intent before resolving  (don't blindly accept "ours" or "theirs.")
- For structured files like `testng.xml` or `pom.xml`, manually merge to avoid duplicate/broken XML nodes.
- Will be using IDE-based merge view for clarity.
- Re-run the affected test suite locally after resolving, before pushing.

### Q11. What is `git cherry-pick` and when have you used it?
**A:** It applies a specific commit from one branch onto another without merging the entire branch. 
	I've used it to **hotfix a critical test script bug** on a release branch without pulling in unrelated in-progress feature commits 
	from `develop`.

### Q13. What's the difference between `git fetch` and `git pull`?
**A:** 
	`fetch` downloads remote changes but doesn't merge them into your local branch — safe to review first. 
	`pull` = `fetch` + `merge` (or rebase, if configured) automatically. 
I prefer `fetch` + review + `merge`/`rebase` manually when working on shared automation frameworks, to avoid unexpected conflicts.

---

## SECTION 3: Maven

### Q15. What is Maven's role in a test automation framework?
**A:** Maven is a **build and dependency management tool**. In automation, it:
- Manages library dependencies (Selenium, TestNG/JUnit, RestAssured, Log4j, etc.) via `pom.xml`.
- Defines build lifecycle phases (compile, test, package) to run and package the framework.
- Integrates with Jenkins to trigger test execution as part of the pipeline.
- Manages **profiles** for running different suites (smoke/regression/environment-specific) via `-P` flag.

### Q16. Explain the Maven build lifecycle phases relevant to test execution.
**A:** Default lifecycle: `validate → compile → test → package → verify → install → deploy`.
For automation, the key ones are:
- `compile` — compiles framework source code.
- `test` — runs unit-level/TestNG tests via Surefire plugin.
- `verify` — often used with Failsafe plugin for integration/UI tests (`*IT.java` naming convention), since Surefire skips these by default.
- `package` — bundles the framework as a JAR, sometimes with dependencies (shaded/fat JAR) for portability.

### Q17. Difference between Surefire and Failsafe plugins?
**A:** 
- **Surefire** runs unit tests during the `test` phase; a failure here **stops the build immediately**.
- **Failsafe** runs integration tests during `integration-test`/`verify` phases; 
	failures don't stop the build immediately — it runs `verify` afterward to fail the build cleanly, 
	allowing **post-test cleanup (like closing browser/DB connections)** to happen even if a test fails.

I use Failsafe for UI/API automation suites since we need teardown to run regardless of test outcome.

Surefire - like HardAssertion - terminets flow on failure 
Failsafe - like SoftAssertion - continues flow even after failure 

### Q18. How do you run specific test suites (smoke vs regression) using Maven?
**A:** Using **Maven profiles** in `pom.xml`, each pointing to a different `testng.xml`:
```
mvn test -Psmoke
mvn test -Pregression -Denv=staging
```
Combined with `-D` system properties to pass runtime parameters (browser, environment, thread count) — these get picked up by TestNG listeners or a config reader class.

### Q20. How is Maven integrated with Jenkins for a CI pipeline?
**A:** Jenkins has a **Maven build step** (or uses a Jenkinsfile with `sh 'mvn clean test'` in a Declarative Pipeline). Jenkins:
- Invokes Maven goals (`clean test -Dsuite=regression.xml`).
- Publishes Surefire/Failsafe XML reports via the **JUnit plugin** or **Allure Jenkins plugin** for visualization.
- Can archive artifacts (JARs, logs, screenshots on failure) via `archiveArtifacts`.

### Q21. What's the difference between `mvn install` and `mvn deploy`?
**A:** 
	`install` places the built artifact into your **local** `.m2` repository (available for other local projects). 
	
	`deploy` pushes it to a **remote repository** (Nexus/Artifactory) so it's shareable across teams/CI agents — 
		useful when the automation framework itself is published as a reusable library for multiple test projects.

---

## SECTION 4: Jenkins

### Q22. Declarative vs Scripted Pipeline — which do you use and why?
**A:** I primarily use **Declarative Pipelines** (`Jenkinsfile` with `pipeline { }` block) — more readable, structured, built-in error handling,
	and easier for teams to maintain. Scripted pipelines (Groovy-based, more flexible) I've used only for complex conditional logic that 
	declarative syntax couldn't handle cleanly, often wrapped inside a `script { }` block within a declarative pipeline.

### Q23. Walk me through a typical Jenkinsfile you've built for test automation.
**A:** 
```groovy
pipeline {
    agent any
    parameters {
        choice(name: 'SUITE', choices: ['smoke','regression'], description: 'Test suite to run')
        choice(name: 'ENV', choices: ['qa','staging'], description: 'Environment')
    }
    stages {
        stage('Checkout') { steps { git branch: 'main', url: '<repo-url>' } }
        stage('Build') { steps { sh 'mvn clean compile' } }
        stage('Test') {
            steps { sh "mvn test -P${params.SUITE} -Denv=${params.ENV}" }
        }
        stage('Report') {
            steps { allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']] }
        }
    }
    post {
        always { junit 'target/surefire-reports/*.xml' }
        failure { mail to: 'team@company.com', subject: "Build Failed: ${env.JOB_NAME}", body: "Check console: ${env.BUILD_URL}" }
    }
}
```
This gives parameterized execution, reporting, and failure notifications in one pipeline.

### Q24. How do you trigger Jenkins jobs automatically (not manually)?
**A:**

Using - **Scheduled builds** (cron syntax) — e.g., nightly full regression at 1 AM.

- **Git webhook triggers** — job runs on every push/PR (via GitHub/GitLab webhook to Jenkins).
- **Poll SCM** — Jenkins periodically checks for changes (less efficient, used when webhooks aren't feasible, e.g., firewall restrictions).
- **Upstream/downstream job triggers** — trigger automation job automatically after a successful deployment job.

### Q25. How do you run tests in parallel in Jenkins to reduce execution time?
**A:** A few approaches I've used:
1. **Jenkins parallel stages** — splitting suite execution across multiple stages/agents in the Jenkinsfile using `parallel { }`.
2. **TestNG parallel execution** (`parallel="methods/classes"` with thread-count in `testng.xml`) within a single Jenkins node.
3. **Selenium Grid / cloud grids (BrowserStack, LambdaTest, Selenoid)** combined with Jenkins to distribute cross-browser tests across multiple nodes simultaneously.
4. **Jenkins agent/node labels** to distribute suite chunks (e.g., split by module) across multiple build agents.

I've reduced regression run time from ~3 hours to under 45 minutes this way on a past project.

### Q26. How do you manage credentials/secrets in Jenkins securely?
**A:** Using the **Jenkins Credentials Manager/Plugin** — storing API keys, DB passwords, and tokens as "Secret text"/"Username-Password" credentials,
	referenced in the pipeline via `credentials()` binding, never hardcoded. Access is scoped per-job/folder to limit exposure.

### Q27. How do you handle Jenkins pipeline failures — what's your debugging approach?
**A:**
1. Check the **console output** first for the failing stage/stack trace.
2. Check if it's an **environment issue** (agent offline, disk space, network) vs an **actual test failure**.
3. Review archived artifacts — screenshots, logs, videos captured on failure (I set this up via a TestNG/JUnit listener that captures on `onTestFailure`).
4. Reproduce locally with the same Maven command/parameters used in the pipeline.
5. If it's intermittent, check the Jenkins build history/trend for a pattern before assuming flakiness.

### Q28. What Jenkins plugins have you used/configured?
Using**A:** Git plugin, Pipeline plugin, JUnit/Extent report plugins, Credentials Binding, Email Extension, 

Blue Ocean (for visualization), Slack Notification, Parameterized Trigger, Docker plugin (for containerized agents), and SonarQube Scanner plugin for code quality gates.

### Q29. What's the difference between a Jenkins Freestyle job and a Pipeline job?
**A:** Freestyle jobs are configured via UI, simpler but not version-controlled and hard to scale/maintain for complex logic. 
	Pipeline jobs are **code-based (Jenkinsfile), version-controlled with the repo**, support complex conditional/parallel logic, and 
	are the industry-standard approach now — I always recommend/migrate teams to Pipeline-as-code.

### Q30. How do you implement quality gates in Jenkins (e.g., block deployment if tests fail)?
**A:** Using `post { failure { ... } }` blocks to halt downstream deployment stages, combined with a **threshold check** — e.g., a script step that parses the Surefire/Allure report and fails the build if pass % is below a defined threshold (say 95%), or if any Priority-1/critical-tagged test fails, even if overall pass rate is acceptable.

---

## SECTION 5: Scenario-Based / Behavioral (Common at Senior Level)

### Q31. A critical release build is failing intermittently in Jenkins but passes locally. How do you approach it?
**A:** 
	Compare environment differences (JDK version, browser version, OS, parallel thread count) between local and CI agent. 
	Check for **test data collisions** if tests run in parallel on CI but sequentially locally. 
	Check resource constraints (Jenkins agent memory/CPU). 
	Add better logging/screenshots on the CI run to capture the exact failure state, 
	and check timing/synchronization issues that only surface under CI's different execution speed.

### Q32. How would you convince a team to migrate from Freestyle Jenkins jobs to Pipeline-as-code?
**A:** Emphasize **version control, auditability (changes reviewed via PR), reusability across projects via shared libraries, and
  easier debugging** since pipeline logic lives in code, not UI clicks that are hard to trace. I'd propose a phased migration — starting with a non-critical job as a proof of concept.

### Q33. How do you structure a CI/CD pipeline to support both manual and automated testing sign-off?
**A:** 
	Automated smoke/regression gates the build first. 
	If it passes, the build auto-deploys to a QA/UAT environment where manual exploratory/UAT testing happens, 
	tracked via a **manual approval step** in Jenkins (`input` step in Declarative Pipeline) before promoting to production — 
	combining automated gating with a controlled manual checkpoint.

---

## Quick Reference: Commands Cheat Sheet

| Tool | Common Commands |
|---|---|
| Git | `git status`, `git rebase -i HEAD~3`, `git cherry-pick <hash>`, `git stash`, `git log --oneline --graph`, `git reflog` |
| Maven | `mvn clean test`, `mvn dependency:tree`, `mvn test -Psmoke -Denv=qa`, `mvn -version`, `mvn help:effective-pom` |
| Jenkins | Declarative `pipeline{}` syntax, `sh`, `parallel{}`, `input`, `credentials()`, `post{always/success/failure}` |

---

*Tip: At 7–9 years, interviewers weigh **decision-making and trade-off reasoning** heavily. 
*For every answer, be ready with a follow-up: "Can you give a real example from your project?" — 
*keep 2–3 concrete stories ready (a flaky test you fixed, a pipeline you optimized, a merge conflict crisis you resolved).*



---- 
### Q19. How do you manage dependency conflicts (diamond dependency problem) in Maven?
**A:** Run `mvn dependency:tree` to identify conflicting transitive dependency versions. Resolve using:
- `<dependencyManagement>` to pin a version project-wide.
- `<exclusions>` to remove an unwanted transitive dependency from a specific library.
This is common with Selenium + other libraries pulling different Guava/Jackson versions — 
	I've had to explicitly pin versions to avoid `NoSuchMethodError` at runtime.


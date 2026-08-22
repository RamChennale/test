# QA Automation (10–12 yrs, BFSI) — TCS Second Round Interview Prep

## What to Expect in the Second Round

At 10-12 years experience, TCS's second round is usually **not a coding-only round** — it blends:

1. **Technical depth** — framework design, tools, architecture decisions you've made
2. **Leadership & people management** — team size, mentoring, appraisals, conflict handling
3. **BFSI domain knowledge** — core banking, payments, cards, insurance, compliance/regulatory testing
4. **Client/stakeholder management** — since TCS is a services company, they check if you can face clients
5. **Process & delivery** — Agile/Scrum, estimation, test strategy, metrics, risk-based testing
6. **Situational/behavioral** — how you handle production defects, deadline pressure, team conflicts

Interviewer is often a **Delivery Manager / Test Architect / Account-level lead**, not just a tech lead. Expect them to probe "tell me about a time" questions heavily.

---

## Section 1: Automation Framework & Architecture

**Q1. Walk me through a test automation framework you designed from scratch.**
A: Structure your answer as: business context → why existing approach was insufficient → framework choice (Hybrid/BDD/Data-driven/Page Object Model) → tech stack (Java/Selenium/TestNG/Cucumber/Rest Assured) → CI/CD integration → reporting (Extent Reports/Allure) → results (e.g., "reduced regression cycle from 5 days to 8 hours, ~70% automation coverage").

**Q2. How do you decide what to automate vs. keep manual?**
A: Use criteria: repeatability, stability of UI/requirements, business criticality, ROI (execution frequency × time saved), regression suite candidates. Avoid automating exploratory, one-time, or highly volatile UI areas.

**Q3. Page Object Model vs. Screenplay pattern — which do you prefer and why?**
A: POM is simpler, widely adopted, good for small-medium teams. Screenplay is more scalable for large suites, promotes composition over inheritance, better for BDD-heavy, multi-actor scenarios (e.g., multiple user roles in a banking workflow).

**Q4. How do you handle flaky tests in a large regression suite?**
A: Root-cause categorize (sync issues, environment/data instability, test interdependency), implement explicit waits over Thread.sleep, isolate test data (avoid shared state), retry-with-analysis (not blind retry), quarantine flaky tests with tracking, and track flakiness % as a metric.

**Q5. How do you manage test data for BFSI applications (accounts, cards, transactions)?**
A: Synthetic data generation respecting masking/PII rules, data seeding via APIs/DB scripts rather than UI, environment-specific data refresh strategy, and strict compliance with data masking (Aadhaar/PAN/account numbers) per RBI/GDPR guidelines.

---

## Section 2: Tools & Technical Depth

**Q6. What's your automation tech stack and why?**
A: Typically: Selenium/Playwright (UI), Java or Python, TestNG/JUnit, Rest Assured or Postman/Newman (API), Cucumber (BDD), Jenkins/Azure DevOps (CI/CD), Maven/Gradle (build), Git (versioning), JMeter/LoadRunner (perf), SonarQube (code quality).

**Q7. How do you test APIs in a payments/core banking system?**
A: Validate contract (schema), status codes, response time SLAs, idempotency (critical for payment retries), negative/boundary scenarios (insufficient funds, invalid account), security headers/token validation, and chaining API calls to simulate end-to-end transaction flows.

**Q8. How is Selenium different from Playwright, and would you migrate?**
A: Playwright offers auto-waiting, faster execution, built-in parallelization, multi-tab/browser context handling, and better handling of modern SPA frameworks. Migration decision depends on team skill, existing framework investment, and app tech stack (React/Angular apps benefit more from Playwright's auto-wait).

**Q9. How do you integrate automation into CI/CD for a banking release cycle?**
A: Trigger smoke suite on every commit/PR, full regression nightly, gate deployment to UAT/staging on smoke pass, integrate with Jenkins/Azure Pipelines, publish reports to dashboards, and enforce quality gates (code coverage, defect density thresholds) before production sign-off.

**Q10. How do you approach performance testing for a high-transaction banking application?**
A: Define NFRs (TPS, response time, concurrent users) from business/SLA docs, use JMeter/LoadRunner, model realistic transaction mixes (login, fund transfer, balance inquiry ratios), test for peak load (month-end/salary days), and monitor server-side metrics (CPU, DB locks, connection pools) alongside client-side response times.

---

## Section 3: BFSI Domain-Specific

**Q11. What are the critical testing areas specific to BFSI applications?**
A: Core banking (CBS) transaction integrity, payment gateway/UPI/NEFT/RTGS/SWIFT flows, reconciliation, interest calculation accuracy, regulatory compliance (RBI, SEBI, IRDAI, SOX, PCI-DSS), fraud detection rules, KYC/AML workflows, and end-of-day batch processing.

**Q12. How do you test end-of-day (EOD)/batch processes in banking?**
A: Validate input file formats, sequencing/dependency of jobs, data reconciliation between source and target, error/exception handling for failed records, restart-ability of batch jobs, and performance within the batch window (critical since banks have fixed processing windows).

**Q13. How do you ensure compliance testing (PCI-DSS, RBI guidelines) in your automation strategy?**
A: Data masking in test environments, audit trail validation, access control/segregation-of-duties testing, encryption validation for data-in-transit/at-rest, and mapping test cases directly to regulatory control requirements for traceability during audits.

**Q14. How do you test a payment reconciliation module?**
A: Validate transaction matching logic (source vs. bank statement), exception handling for mismatches, duplicate detection, partial settlements, currency conversion accuracy (for cross-border), and reporting for unreconciled items.

---

## Section 4: Process, Agile & Metrics

**Q15. How do you estimate automation effort for a new project?**
A: Break down by: number of test scenarios × complexity factor, framework setup time (if greenfield), environment stabilization buffer, historical velocity data from past sprints, and contingency (15-20%) for flaky/unstable applications.

**Q16. What metrics do you report to stakeholders?**
A: Automation coverage %, defect leakage/escape rate, test execution pass %, cycle time reduction, defect density, mean time to detect (MTTD), and ROI of automation (hours saved vs. investment).

**Q17. How do you handle a situation where automation is falling behind development velocity?**
A: Prioritize automation for high-risk/high-frequency areas first, use shift-left approaches (API-level automation before UI is stable), parallelize execution, and negotiate with Scrum team for automation-focused sprints or dedicated SDET capacity.

**Q18. Describe your defect triage process.**
A: Daily/alternate-day triage with dev+QA+BA, severity/priority classification, root cause categorization (requirement gap, code defect, environment, data), SLA-based aging tracking, and trend analysis to identify problem modules.

---

## Section 5: Leadership & Behavioral (Very Important at This Level)

**Q19. Tell me about a time you had a conflict with a developer/team member — how did you resolve it?**
A: Use STAR format. Focus on data-driven resolution (showing defect evidence, logs) rather than escalation-first approach; emphasize collaboration and preserving relationship.

**Q20. How do you mentor junior QA engineers in your team?**
A: Structured onboarding, pair-testing/pair-automation sessions, code review with constructive feedback, encouraging certifications (ISTQB, Selenium), and creating growth paths (manual → automation → SDET).

**Q21. Describe a critical production defect that slipped through — how did you handle it?**
A: RCA-driven answer: immediate hotfix support, root cause (test gap identification), corrective action (added regression case, review process change), and communication to stakeholders/client.

**Q22. How do you handle tight deadlines with reduced testing time?**
A: Risk-based testing — prioritize by business impact and usage frequency, negotiate scope with PM (partial UAT sign-off with known risk documented), leverage automation for regression, and ensure transparent risk communication rather than silently cutting corners.

**Q23. How many people have you led, and how do you manage performance appraisals?**
A: Be ready with concrete numbers (team size, onshore/offshore split), your appraisal approach (goal-setting via KPIs, mid-year check-ins, calibrated ratings), and examples of performance improvement plans you've run.

**Q24. Why TCS, and why this role at this stage of your career?**
A: Tie to TCS's scale in BFSI accounts, opportunity to lead larger automation/COE (Center of Excellence) initiatives, and alignment with your growth toward Test Architect/Delivery Manager track.

---

## Section 6: Likely Curveball / Scenario Questions

**Q25. A client wants 100% automation — how do you respond?**
A: Educate on ROI reality — not everything is automatable cost-effectively (CAPTCHA, OTP-heavy flows, one-time UAT scenarios); propose a phased, risk-prioritized roadmap instead of blanket promises.

**Q26. Your onshore/offshore team is in different time zones — how do you ensure quality without overlap?**
A: Structured handover documents, overlapping "golden hours" for sync calls, clear Definition of Done, automated status dashboards, and async communication via JIRA/Confluence to reduce dependency on real-time overlap.

**Q27. How would you set up a Test Center of Excellence (TCoE) or automation COE?**
A: Standardized frameworks/reusable libraries across projects, common tooling, governance for framework versioning, training programs, and centralized metrics/dashboard reporting across accounts.

---

## Quick Prep Checklist Before the Interview

- [ ] Prepare 3–4 STAR stories (conflict, production issue, deadline pressure, mentoring)
- [ ] Have concrete numbers ready: team size, coverage %, cycle time reduction, defect leakage
- [ ] Refresh on current tools even if not daily-hands-on (Playwright, Rest Assured, Cypress, K6)
- [ ] Know 2–3 BFSI-specific compliance terms (PCI-DSS, RBI guidelines, SOX) and how they map to testing
- [ ] Prepare 2–3 thoughtful questions to ask the interviewer (about the account, team structure, tech modernization plans)
- [ ] Be ready to whiteboard/verbally describe a framework architecture diagram if asked

Good luck with your round.

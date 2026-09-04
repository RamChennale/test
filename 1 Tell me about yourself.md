Tell me about yourself

Structure your answer in 4 parts (keep it 60–90 seconds):
1. **Current role** – 
"I am working as an Senior Member Technical with  total 9 years of experience Software testing and from last 5 and half years worked in Broadridge."

2. **Tech stack** – 
"Currently I'm working both Functional Testing and Autoimation - with 
						Selenium WebDriver with Java, 
						TestNG, Maven, and 
						also worked on REST Assured for API automation in the previous project. 
						along with CI/CD tools like Jenkins and version control using Git."
					
3. **What you do day-to-day** – 
"I design TC / Test plan/ Test strategy, defect reporting, and adding test scripts for regression suites forthe completed functionality to automation frameworks at the end of the sprint"
				
4. **Wrap-up/why this role** – 
"I'm looking for an opportunity where I can apply my skillset of financial capital market  on a larger scale 
						and 


***Q: Explain Your Automation Framework Experience in Interviews??

A question that comes up again and again for Automation Test Engineers is: "Can you walk me through the automation framework you've worked on?"
Sample answer:
I've worked on a Hybrid Automation Framework that brings together the Page Object Model (POM), data-driven testing, and TestNG.

1.Tech stack:
	Java
	Selenium WebDriver
	TestNG
	Maven
	Reporting and monitoring:

2.Extent Reports for HTML-based test reports
3.Log4j for logging
4.Jenkins for CI/CD integration

5.Framework structure - Architecture (layered):
	1.	Base classes handle setup and teardown
	2.  Page classes hold element locators and actions
	3.	Test classes contain the actual validations
	4.	Utility classes cover reusable functions — reading test data, capturing screenshots, and other common operations
	5.	To keep things maintainable, the framework follows POM and pulls test data from external sources like Excel, JSON, or properties files.

For faster execution, it relies on:
6.TestNG parallel execution

7.A few more built-in features:

Auto-retry for failed tests
Screenshot capture on failure
Environment config management (QA/UAT/Prod)
Dynamic test data generation via Faker

8.Challenges faced: Flaky tests and data conflicts during parallel runs — solved using explicit waits, better synchronization

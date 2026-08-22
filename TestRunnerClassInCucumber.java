/*TestRunner class - 
The TestRunner class acts as the engine and configuration hub that links your human-readable Gherkin feature files to your actual 
Java stepdefinition code, using TestNG as the execution framework. 

Links Gherkin_feature --> to -->Java stepdefinition code: using TestNG Framework

It does not contain any test logic itself; 
it uses the @CucumberOptions annotation to define exactly 
which tests to run, 
where to find them, 
Where are the freature files and correponding stepdefinition and 
how to report the results.
*/ 

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.example.stepdefs"},
        tags = "@Regression",
        plugin = {
                "pretty",
                "summary",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true
        // tags = "@create or @read or @update or @delete" // uncomment to filter by tag
)
public class TestRunner extends AbstractTestNGCucumberTests {
}


public class TestRunner extends AbstractTestNGCucumberTests

	/*extends AbstractTestNGCucumberTests: This is critical for TestNG integration. 
	It hooks Cucumber into the TestNG lifecycle, allowing you to run your feature files as TestNG tests via building tools (like Maven or Gradle)
	or your IDE.*/
	
2. @CucumberOptions Parameters:

features = "src/test/resources/features
	"Tells Cucumber where your.
	feature files are stored.It scans this folder for your Gherkin scenarios (Given, When, Then)
.glue = {"com.example.stepdefs"}Tells Cucumber which package contains the Java code (Step Definitions) that matches the steps in your feature files
.tags = "@Regression"Filters which tests to execute. Only scenarios annotated with @Regression will run.
	Your commented-out line // tags = "@create or @read..." 
	shows how you can combine tags using logical operators (or, and, not).plugin = { ... }
	Configures the test reporting and console output 

plugins:
"pretty": Prints the Gherkin steps in the console color-coded with status (passed, failed)
."summary": Prints a strict count of passed/failed scenarios and suggestions for missing steps at the end
."html:target/cucumber-reports/cucumber.html": Generates a built-in, user-friendly HTML report
."json:target/cucumber-reports/cucumber.json": Generates a raw JSON report (often used to feed data into external reporting tools like 
	Jenkins or ExtentReports)
.monochrome = trueCleans up the console output by disabling strange ASCII characters or unreadable formatting, 
	making the logs easy to read.
package CucumberTestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features="src/test/java/Features", // Feature file path
		glue= "stepDefinitions", //Step definition package name
		monochrome=true
	)

public class TestRunner extends AbstractTestNGCucumberTests {

}

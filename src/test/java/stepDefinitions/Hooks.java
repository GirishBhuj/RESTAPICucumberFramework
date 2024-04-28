package stepDefinitions;

import java.io.IOException;
import org.apache.logging.log4j.*;
import io.cucumber.java.Before;

public class Hooks {

	@Before()
	public void beforeScenario() throws IOException
	{	

		System.setProperty("log4j.configurationFile","log4j2.xml");
	}
}

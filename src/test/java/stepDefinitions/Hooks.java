package stepDefinitions;

import java.io.IOException;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import resources.ExtentManager;
import resources.Utils;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

	@Before()
	public void beforeScenario() throws IOException
	{	
		System.setProperty("log4j.configurationFile","log4j2.xml");
	}
	
	@Before("@AddPlaceTest")
	public void beforeAddPlaceScenario() throws IOException
	{	
	}
	
	@After("@AddPlaceTest")
	public void AfterAddPlace() throws IOException
	{	

		//System.out.println("In AfterAddPlace() of Hooks");;
	}
	
	@Before("@AddPunePlaceTest")
	public void beforeAddPunePlaceScenario() throws IOException
	{	
	}
	
	@After("@AddPunePlaceTest")
	public void AfterAddPunePlace() throws IOException
	{	
	}
	
	@After()
	public void AfterScenario() throws IOException
	{	

		//System.out.println("In After() of Hooks");;
	}
}

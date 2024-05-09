package resources;

import java.io.IOException;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {
	
	private static ExtentSparkReporter report;
	private static ExtentReports extent;
	static Date d;
	
	//Will create a function to create an instance of ExtentSparkReporter class if the instance is null
	//This class is used to define the name of the report, load the report config xml file

	
	public static ExtentSparkReporter getInstanceExtentSparkReporter() {
		try {
			d = new Date();
			String dat = d.toString().replace(" ", "_").replace(":", "_");
			if (report ==null) {
				report = new ExtentSparkReporter(System.getProperty("user.dir")+"\\ExtentReport\\Add Place API Testing"+"_"+dat+".html");
				//Load the Configuration file
				report.loadXMLConfig(System.getProperty("user.dir")+"\\ExtentReportConfig\\Report-Config.xml");
			} 
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return report;
	}
	
	//Will create a function to create an instance of ExtentReports class if the instance is null
	//This class is used to add the report and add system info, used to create test case, flush out the report in HTML format

	public static  ExtentReports getInstanceExtentReports() {
		if(extent == null) {
			extent = new ExtentReports();
			//Add the report to the class 
			extent.attachReporter(report);
			// Add System Info - add systetm info class
			extent.setSystemInfo("OS", "Win 11 Pro");
			extent.setSystemInfo("Author", "Girish");
			extent.setSystemInfo("Env", "SIT");
			extent.setSystemInfo("Test Cycle", "Initial");
			extent.setSystemInfo("App", "Rediffmail");
			extent.setSystemInfo("Company", "xyz");
		}
		
		return extent;
	}
	
	
	
}

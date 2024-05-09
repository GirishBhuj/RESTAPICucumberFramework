package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification mReqSepc;
	Logger logger = LogManager.getLogger(Utils.class);
	
	public RequestSpecification CreateRequestSpecification() throws IOException
	{
		if(mReqSepc==null)
		{
		PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
	
		mReqSepc=new RequestSpecBuilder().
				setBaseUri(getGlobalValue("baseUrl")).
				addQueryParam("key", "qaclick123").
				addFilter(RequestLoggingFilter.logRequestTo(log)).
				addFilter(ResponseLoggingFilter.logResponseTo(log)).
				setContentType(ContentType.JSON).build().log().all();
		
		/*mReqSepc=new RequestSpecBuilder().
				setBaseUri(getGlobalValue("baseUrl")).
				addQueryParam("key", "qaclick123").
				addFilter(RequestLoggingFilter.logRequestTo(log)).
				addFilter(ResponseLoggingFilter.logResponseTo(log)).
				setContentType(ContentType.JSON).build();
		*/
		
		//System.out.println("========= Inside CreateRequestSpecification");
		//System.out.println(mReqSepc);
		//System.out.println("=============");
		
  		//logger.info("Inside CreateRequestSpecification(): "+ mReqSepc);
		return mReqSepc;
		}
		return mReqSepc;	
	}
	
	
	public static String getGlobalValue(String key) throws IOException
	{
		Properties prop =new Properties();
		FileInputStream fis =new FileInputStream
		(System.getProperty("user.dir") +
		"\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		//System.out.println("=== in getGlobalValue "+ prop.getProperty(key));
		return prop.getProperty(key);		
	}
	
	public String getJsonPath(Response response,String key)
	{		
		  try {
			  	String resp=response.asString();
				JsonPath   js = new JsonPath(resp);
				return js.get(key).toString();
		  } 
		  catch (Exception e) {
			  	System.out.println("=========> In catch block Failed to parse the JSON document :"+ e);
		        StringBuilder errorMsg = new StringBuilder();
		        for (StackTraceElement element : e.getStackTrace()) {
		        	errorMsg.append(element.toString());
		        	errorMsg.append("\n");
		        }
		        //Assert.fail(); // To stop the test

		        logger.error(errorMsg);
		  		return "Invalid key" + errorMsg;
		  		}
	}
}
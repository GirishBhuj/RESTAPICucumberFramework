package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import stepDefinitions.AddPlaceStepDefinitions;

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
		
		/* ReqSepc =new RequestSpecBuilder().
				setBaseUri("https://rahulshettyacademy.com").
				addQueryParam("key", "qaclick123").
				addFilter(RequestLoggingFilter.logRequestTo(log)).
				addFilter(ResponseLoggingFilter.logResponseTo(log)).
				setContentType(ContentType.JSON).build().log().all();
		 */
	
		//System.out.println("========= Inside CreateRequestSpecification");
		//System.out.println(mReqSepc);
		//System.out.println("=============");
		
  		logger.info("Inside CreateRequestSpecification(): "+ mReqSepc);
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
		  String resp=response.asString();
		JsonPath   js = new JsonPath(resp);
		return js.get(key).toString();
	}
}
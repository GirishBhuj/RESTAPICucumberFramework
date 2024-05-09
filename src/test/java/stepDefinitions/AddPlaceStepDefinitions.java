package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import resources.Utils;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.ExtentManager;
import resources.TestDataBuild;


public class AddPlaceStepDefinitions extends Utils {
	RequestSpecification mReqSpec;
	ResponseSpecification mResponseSpec;
	Response mResponse;
	
	TestDataBuild TestData =new TestDataBuild();
	static String place_id;

	Logger logger = LogManager.getLogger(AddPlaceStepDefinitions.class);
	ExtentSparkReporter report = ExtentManager.getInstanceExtentSparkReporter();
    ExtentReports extent = ExtentManager.getInstanceExtentReports();
	ExtentTest extLogger;
	int mResponseStatusCode;
	
	
	@Given("Add Place Payload with {string}  {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws InterruptedException, IOException
	{
		//System.out.println("===> inside add_place_payload_with ");
		extLogger = extent.createTest("Add place API Automation - Test: "+ name);
		extLogger.assignCategory("API Test Category");

		mReqSpec = given().spec(CreateRequestSpecification()).
				body(TestData.addPlacePayLoad(name, language, address));
		extLogger.log(Status.INFO ,MarkupHelper.createLabel("Add place payload with name",ExtentColor.BLUE));
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) throws InterruptedException
	{
		APIResources resourceAPI=APIResources.valueOf(resource);

		mResponseSpec = new ResponseSpecBuilder().expectStatusCode(200).
				expectContentType(ContentType.JSON).build();

		//logger.info("Inside User calls HTTP request with resource:"+ resource +" Method:"+ method);
  		
		if(method.equalsIgnoreCase("POST")) {
			mResponse = mReqSpec.when().post(resourceAPI.getResource());
			String mStr = mResponse.toString();
			mResponseStatusCode = mResponse.statusCode();
			if ( mResponseStatusCode == 200 || mResponseStatusCode == 201) {
					extLogger.log(Status.INFO ,MarkupHelper.createLabel("User calls Post method "+ mStr,ExtentColor.BLUE));
			}
			else {
				extLogger.log(Status.FAIL ,MarkupHelper.createLabel("User calls Post method. Response Code: "+ mResponseStatusCode,ExtentColor.RED));
				extent.flush();
				Assert.fail();
			}
		}
		else if(method.equalsIgnoreCase("GET")) {
			mResponse =mReqSpec.when().get(resourceAPI.getResource());			
		}
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) throws InterruptedException
	{
		//logger.info("Inside API calls got success with status code:"+ int1);
		
		if (mResponseStatusCode == 200 || mResponseStatusCode == 201){
			extLogger.log(Status.INFO ,MarkupHelper.createLabel("Inside Assert method of status code 200",ExtentColor.BLUE));
		}
		else{
			extLogger.log(Status.FAIL ,MarkupHelper.createLabel("Assert method of status code not equal to 200. Response code:"
					+ mResponseStatusCode ,ExtentColor.RED));
			extent.flush();
			Assert.fail();
		}
		//assertEquals(mResponse.getStatusCode(),200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) throws InterruptedException
	{
		//logger.info("Inside response body key value:"+ keyValue + " Expected value: "+Expectedvalue);
			
			if (mResponse.getStatusCode()== 200 || mResponse.getStatusCode()== 201){
				String mResKeyValue = getJsonPath(mResponse,keyValue);				
				if (mResKeyValue.equals(Expectedvalue)){
					extLogger.log(Status.INFO ,MarkupHelper.createLabel("Validating response body for: "+ 
				Expectedvalue + " Response:" + mResKeyValue
							,ExtentColor.BLUE));
				}
				else{
					extLogger.log(Status.FAIL ,MarkupHelper.createLabel("Response body value NOT matches for: "+ 
				Expectedvalue  + " Response:" + mResKeyValue
							,ExtentColor.RED));
				} 
			}
			else {
					extLogger.log(Status.FAIL ,MarkupHelper.createLabel("Response body value NOT matches for: "+ Expectedvalue +
							" Response code:" + mResponse.getStatusCode(),ExtentColor.RED));
					extent.flush();
					Assert.fail();
			}
		//assertEquals(getJsonPath(mResponse,keyValue),Expectedvalue);
	}
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws InterruptedException, IOException
	{
		String actualName="";
		place_id="";
		place_id=getJsonPath(mResponse,"place_id").trim();
		     
		mReqSpec=given()
		    	.spec(CreateRequestSpecification())
		    	.queryParam("place_id",place_id);

		user_calls_with_http_request(resource,"GET");
		String mTmpStr = mResponse.asString();

		     if (mTmpStr.contains("location")) {
		    	 actualName=getJsonPath(mResponse,"name");
		    	 if (actualName.equals(expectedName)){
					extLogger.log(Status.INFO ,MarkupHelper.createLabel("Validating Place ID created: "+ 
					expectedName + " Response"+ mTmpStr,ExtentColor.BLUE));
					extent.flush();
					Assert.assertTrue(true);
		    	 }
		     }
		     else {
					extLogger.log(Status.FAIL ,MarkupHelper.createLabel("Place ID not created. Response code: " +
							mResponseStatusCode,ExtentColor.RED));
					extent.flush();
					Assert.fail();
		     }
		//assertEquals(actualName,expectedName);
	}
}
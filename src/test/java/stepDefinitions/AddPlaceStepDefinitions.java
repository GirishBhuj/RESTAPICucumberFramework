package stepDefinitions;

import static org.testng.AssertJUnit.assertEquals;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import resources.Utils;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;

public class AddPlaceStepDefinitions extends Utils {
	RequestSpecification mReqSpec;
	ResponseSpecification mResponseSpec;
	Response mResponse;
	TestDataBuild TestData =new TestDataBuild();
	static String place_id;

	Logger logger = LogManager.getLogger(AddPlaceStepDefinitions.class);

	@Given("Add Place Payload with {string}  {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws InterruptedException, IOException
	{
		//System.out.println("===> inside add_place_payload_with ");
  		logger.info("Inside Add place payload with name: "+name +" Language:"+ language + " Address"+ address);
		mReqSpec = given().spec(CreateRequestSpecification()).
				body(TestData.addPlacePayLoad(name, language, address));	
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) throws InterruptedException
	{
		APIResources resourceAPI=APIResources.valueOf(resource);

		mResponseSpec = new ResponseSpecBuilder().expectStatusCode(200).
				expectContentType(ContentType.JSON).build();

		logger.info("Inside User calls HTTP request with resource:"+ resource +" Method:"+ method);
  		
		if(method.equalsIgnoreCase("POST"))
			mResponse = mReqSpec.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			mResponse =mReqSpec.when().get(resourceAPI.getResource());
	}
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) throws InterruptedException
	{
		logger.info("Inside API calls got success with status code:"+ int1);
		assertEquals(mResponse.getStatusCode(),200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) throws InterruptedException
	{
		logger.info("Inside response body key value:"+ keyValue + " Expected value: "+Expectedvalue);
		assertEquals(getJsonPath(mResponse,keyValue),Expectedvalue);
	}
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws InterruptedException, IOException
	{
	     place_id=getJsonPath(mResponse,"place_id");
	     mReqSpec=given().spec(CreateRequestSpecification()).
	    		 queryParam("place_id",place_id);
	     
		 user_calls_with_http_request(resource,"GET");
		 
		String actualName=getJsonPath(mResponse,"name");
		logger.info("Inside Verify place ID is crerated Expected Name:"+ expectedName + " Resource: "+resource);
		 
		assertEquals(actualName,expectedName);
	}

}

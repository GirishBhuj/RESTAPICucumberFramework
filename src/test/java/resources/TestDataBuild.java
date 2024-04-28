package resources;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import POJO.AddPlace;
import POJO.Location;
import stepDefinitions.AddPlaceStepDefinitions;

public class TestDataBuild {

	Logger logger = LogManager.getLogger(TestDataBuild.class);
	public AddPlace addPlacePayLoad(String name, String language, String address)
	{
  		AddPlace mPlaceDtls =new AddPlace();
		mPlaceDtls.setAccuracy(60);
		mPlaceDtls.setAddress(address);
		mPlaceDtls.setLanguage(language);
		mPlaceDtls.setPhone_number("(+91) 983 893 3900");
		mPlaceDtls.setWebsite("https://bamburda.com");
		mPlaceDtls.setName(name);
		List<String> myList =new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");

		mPlaceDtls.setTypes(myList);
		Location mLocation =new Location();
		mLocation.setLat(-38.383494);
		mLocation.setLng(33.427362);
		mPlaceDtls.setLocation(mLocation);
	
  		logger.info("Inside TestDataBulild() : "
  				+ name +
  				" Language:"+ language + 
  				" Address"+ address + 
  				" Phone number"+ mPlaceDtls.getPhone_number() + 
  				" Web site"+ mPlaceDtls.getWebsite() 
  				);

		return mPlaceDtls;
	}
	
	public String deletePlacePayload(String placeId)
	{
		return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
	}
}

package restAssuredBasics;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath convertStringToJson(String response) {
		
		// This method can be implemented to convert a String response to a JsonPath object
		// for easier JSON parsing and extraction of values.
		
		JsonPath js = new JsonPath(response);
		return js;
		
	}

}

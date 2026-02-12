package restAssuredBasics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import files.TestData;

public class FirstProgramRestAssured {
	
	@DataProvider(name = "getData")
	public Object[][] getData() {
		
		baseURI = "https://rahulshettyacademy.com";
		String name = "Frontline house";
		String language = "French-IN";
		String address = "29, side layout, cohen 09";
		String phoneNumber = "(+91) 983 893 3937";
		String newAddress = "70 Hidden Drive, AB, Canada ";
		
		return new Object[][] {{name, language, address, phoneNumber, newAddress}};

		
	}
	
	@DataProvider(name = "getData_JSON_file")
	public Object[][] getData_JSON_file() throws IOException {
		
		///Users/naveennarayanan99/RestAssured/RestAssuredAPI_Automation/src/files/TestDataJSON.JSON]		
		Path path = Paths.get(System.getProperty("user.dir"), "src", "files", "TestDataJSON_old_address.JSON");		
		String body_old_address = Files.readString(path);
		String newAddress = "70 Hidden Drive, AB, Canada ";
		return new Object[][]{ {body_old_address, newAddress}};
		
	}

	@Test (dataProvider = "getData")
	public void e2eTestWithValidData(String name, String language, String address, String phoneNumber, String newAddress) {

		baseURI = "https://rahulshettyacademy.com";
		JsonPath js;
		String response;

		response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(TestData.addPlacePayload(name, language, address, phoneNumber)).when()
				.post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.header("Server", containsString("Apache")).body("scope", equalTo("APP")).log().all().extract()
				.response().asString();

		js = ReusableMethods.convertStringToJson(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);

		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.body(TestData.updatePlacePayload(placeId, newAddress)).when().put("/maps/api/place/update/json").then()
				.assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).log().all();

		response = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).when()
				.get("/maps/api/place/get/json").then().assertThat().statusCode(200)
				.body("address", equalTo(newAddress)).log().all().extract().response().asString();
		
		js = ReusableMethods.convertStringToJson(response);
		String actualAddress = js.getString("address");
		
		Assert.assertEquals(actualAddress, newAddress);

		given().log().all().queryParam("key", "qaclick123").body(TestData.deletePlacePayload(placeId)).when()
				.delete("/maps/api/place/delete/json").then().assertThat().statusCode(200).log().all();

		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).when()
				.get("/maps/api/place/get/json").then().assertThat().statusCode(404).log().all();

	}
	
	@Test (dataProvider = "getData_JSON_file")
	public void e2eTestWithJSONfile(String body, String newAddress) {

		baseURI = "https://rahulshettyacademy.com";
		JsonPath js;
		String response;

		response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(body).when()
				.post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.header("Server", containsString("Apache")).body("scope", equalTo("APP")).log().all().extract()
				.response().asString();

		js = ReusableMethods.convertStringToJson(response);
		String placeId = js.getString("place_id");

		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.body(TestData.updatePlacePayload(placeId, newAddress)).when().put("/maps/api/place/update/json").then()
				.assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).log().all();

		response = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).when()
				.get("/maps/api/place/get/json").then().assertThat().statusCode(200)
				.body("address", equalTo(newAddress)).log().all().extract().response().asString();
		
		js = ReusableMethods.convertStringToJson(response);
		String actualAddress = js.getString("address");
		
		Assert.assertEquals(actualAddress, newAddress);

		given().log().all().queryParam("key", "qaclick123").body(TestData.deletePlacePayload(placeId)).when()
				.delete("/maps/api/place/delete/json").then().assertThat().statusCode(200).log().all();

		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).when()
				.get("/maps/api/place/get/json").then().assertThat().statusCode(404).log().all();

	}

}

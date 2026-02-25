package restAssuredBasics;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class OAuth_Access_Token {

	@BeforeMethod
	public void setup() {
		baseURI = "https://rahulshettyacademy.com";
	}

	@Test
	public void accessTokenTest() {

		String response = given().log().all()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").when().post("oauthapi/oauth2/resourceOwner/token").then().log().all()
				.extract().asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");
		
		response = given().log().all().queryParam("access_token", accessToken).when().get("/oauthapi/getCourseDetails")
				.then().log().all().extract().asString();
		
		System.out.println(response);

	}

}
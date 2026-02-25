package com.naveen.api.RestAPIFramework;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class GetUsersTest extends BaseTest {

	@Test
	public void getUsersTest() {

		given()
			.log().all().spec(requestSpec).
		when()
			.get("/users").
		then().
	    	spec(responseSpec)
	    	.body("users.size()", greaterThan(0))
	    	.body("users[0].id", notNullValue())
	    	.body("users[0].firstName", notNullValue());

	}
	
	@Test
	public void getUsersTest_queryParam() {

		given()
			.log().all().spec(requestSpec).queryParam("limit", 10).
		when()
			.get("/users").
		then()
			.spec(responseSpec)
	        .body("users.size()", equalTo(10));

	}
	
	@Test
	public void getUsersTest_pathParam() {

		given()
			.log().all().spec(requestSpec).pathParam("id", 1).
		when()
			.get("/users/{id}").
		then().
			log().all().spec(responseSpec)
			.body("id", equalTo(1));

	}
	
	@Test
	public void getUsersTest_extractIDs() {

		List <Integer> ids = given()
			.log().all().spec(requestSpec).
		when()
			.get("/users").
		then().
			log().all().spec(responseSpec).extract().path("users.id");
		System.out.println(ids);
		
		Assert.assertTrue(ids.contains(1));
		Assert.assertTrue(ids.size() > 0);
	}
	
	@Test
	public void getUsersTest_withJSONschema_Hardcorded() {

		given()
			.log().all().spec(requestSpec).
		when()
			.get("/users").
		then().
			log().all().spec(responseSpec)
			.body(matchesJsonSchemaInClasspath("schema/users-schema.json"));

	}
	
	@Test
	public void getInvalidUser() {

	    given()
	        .spec(requestSpec)
	        .pathParam("id", 99999)
	    .when()
	        .get("/users/{id}")
	    .then()
	        .statusCode(404).time(lessThan(2000L));
	}
}
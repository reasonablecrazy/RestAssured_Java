package com.naveen.api.RestAPIFramework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class DebugTest {

    @Test
    public void debugApi() {

        Response response = RestAssured
                .given()
                .header("User-Agent", "Mozilla/5.0")
                .when()
                .get("https://reqres.in/api/users?page=2");

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: ");
        response.prettyPrint();
    }
}
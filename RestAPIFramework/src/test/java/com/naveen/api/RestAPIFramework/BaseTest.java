package com.naveen.api.RestAPIFramework;

import org.testng.annotations.BeforeClass;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;


public class BaseTest {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {

        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .addHeader("Content-Type", "application/json")
                .build();

        RestAssured.requestSpecification = requestSpec;
    }
    
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setupResponseSpec() {

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .build();
    }
}
package com.api.tests;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.utils.AuthTokenGenerator;

import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterApiTest {
	@Test
	public void masterApiTest() throws IOException {
		given().baseUri(getProperty("BASE_URI")).and().header("Authorization", AuthTokenGenerator.getToken(FD)).and().contentType("").and()
				.log().all().when().post("/master").then().log().body().and().statusCode(200).and()
				.time(lessThan(1000L)).body("message", equalTo("Success")).and().body("data", notNullValue()).and()
				.body("data", hasKey("mst_oem")).and().body("$", hasKey("message"))
				.body("data.mst_oem.size()", greaterThan(0))
				.and().body("data.mst_model.size()", greaterThan(0))
				.and().body("data.mst_oem.id", everyItem(greaterThan(0)))
				.and().body( JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterApiSchema.json"));
	}
	
	@Test
	public void masterApi_InvalidToken_Test() throws IOException {
		given().baseUri(getProperty("BASE_URI")).and().header("Authorization", "3425gdhsdbsj").and().contentType("").and()
				.log().all().when().post("/master").then().log().body().and().statusCode(500);
	}

}

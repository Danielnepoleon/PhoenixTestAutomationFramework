package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.utils.AuthTokenGenerator;
import com.api.utils.ConfigManager;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CountApiTest {
	
	@Test
	public void countApiTest() {
		try {
			given().baseUri(ConfigManager.getProperty("BASE_URI")).accept(ContentType.JSON).and().header("Authorization", AuthTokenGenerator.getToken(FD))
			.when().get("/dashboard/count").then().log().body().and().statusCode(200).body("message", equalTo("Success"))
			.and().time(lessThan(1000L)).and().body("data", notNullValue()).and().body("data.size()", greaterThanOrEqualTo(3))
			.and().body("data.count", everyItem(greaterThanOrEqualTo(0))).and().body("data.label", everyItem(not(blankOrNullString())))
			.and().body("data.key", containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
			.and().body(matchesJsonSchemaInClasspath("response-schema/CountApiSchema.json"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void countApiTestwithoutHeader() {
		try {
			given().baseUri(ConfigManager.getProperty("BASE_URI")).accept(ContentType.JSON).and()
			.when().get("/dashboard/count").then().log().body().and().statusCode(401);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

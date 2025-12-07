package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;

import com.api.constants.Role;
import com.api.utils.AuthTokenGenerator;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtils;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CountApiTest {
	
	@Test
	public void countApiTest() {
		given().spec(SpecUtils.requestSpecWithAuth(Role.FD))
		.when().get("/dashboard/count").then().log().body().spec(SpecUtils.responseSpec()).body("message", equalTo("Success")).and().body("data", notNullValue()).and().body("data.size()", greaterThanOrEqualTo(3))
		.and().body("data.count", everyItem(greaterThanOrEqualTo(0))).and().body("data.label", everyItem(not(blankOrNullString())))
		.and().body("data.key", containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
		.and().body(matchesJsonSchemaInClasspath("response-schema/CountApiSchema.json"));
	}

	@Test
	public void countApiTestwithoutHeader() {
		given().spec(SpecUtils.requestSpec()).and()
		.when().get("/dashboard/count").then().spec(SpecUtils.responseSpecWithStatusCheck(401));
	}
}

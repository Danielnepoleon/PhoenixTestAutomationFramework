package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import com.api.constants.Role;
import static com.api.utils.SpecUtils.*;

public class CountApiTest {

	@Test(description = "Verify if the count api shows response correctly", groups = { "api", "regression", "smoke" })
	public void countApiTest() {
		given().spec(requestSpecWithAuth(Role.FD)).when().get("/dashboard/count").then().log().body()
				.spec(responseSpec_OK()).body("message", equalTo("Success")).and().body("data", notNullValue()).and()
				.body("data.size()", greaterThanOrEqualTo(3)).and()
				.body("data.count", everyItem(greaterThanOrEqualTo(0))).and()
				.body("data.label", everyItem(not(blankOrNullString()))).and()
				.body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"))
				.and().body(matchesJsonSchemaInClasspath("response-schema/CountApiSchema.json"));
	}

	@Test(description = "Verify if count api gives correct status code for the invalid token", groups = { "api",
			"regression", "smoke", "negative" })
	public void countApiTestwithoutHeader() {
		given().spec(requestSpec()).and().when().get("/dashboard/count").then()
				.spec(responseSpec_TEXT(401));
	}
}

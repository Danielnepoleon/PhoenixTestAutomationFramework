package com.api.tests;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.constants.Role;
import static com.api.utils.SpecUtils.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterApiTest {
	@Test(description = "Verify if the master api shows response correctly", groups = { "api", "regression", "smoke" })
	public void masterApiTest() throws IOException {
		given().spec(requestSpecWithAuth(Role.FD)).when().post("/master").then().spec(responseSpec())
				.body("message", equalTo("Success")).and().body("data", notNullValue()).and()
				.body("data", hasKey("mst_oem")).and().body("$", hasKey("message"))
				.body("data.mst_oem.size()", greaterThan(0)).and().body("data.mst_model.size()", greaterThan(0)).and()
				.body("data.mst_oem.id", everyItem(greaterThan(0))).and()
				.body(matchesJsonSchemaInClasspath("response-schema/MasterApiSchema.json"));
	}

	@Test(description = "Verify if master api gives correct status code for the invalid token", groups = { "api",
			"regression", "smoke", "negative" })
	public void masterApi_InvalidToken_Test() throws IOException {
		given().baseUri(getProperty("BASE_URI")).and().header("Authorization", "3425gdhsdbsj").and().log().all().when()
				.post("/master").then().spec(responseSpecWithStatusCheck(500));
	}

}

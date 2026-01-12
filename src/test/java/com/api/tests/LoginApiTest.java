package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.Usercredentials;
import static com.api.utils.SpecUtils.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginApiTest {
	private Usercredentials credentials;

	@BeforeMethod(description = "Create payload for the login api")
	public void setUp() {
		credentials = new Usercredentials("iamfd", "password");
	}

	@Test(description = "Verify if login api is working for user FD", groups = { "api", "regression", "smoke" })
	public void loginApiTest() throws IOException {
		given().spec(requestSpecWithoutAuth()).body(credentials).when().post("login").then().log().all().spec(responseSpec()).and()
				.body("message", equalTo("Success")).and().body("data.token", notNullValue()).and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginApiSchema.json"));
	}

}

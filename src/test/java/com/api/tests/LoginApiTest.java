package com.api.tests;

import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.Usercredentials;
import com.api.services.AuthService;

public class LoginApiTest {
	private Usercredentials credentials;
	private AuthService authService;

	@BeforeMethod(description = "Create payload for the login api & Setting up the auth service reference")
	public void setUp() {
		credentials = new Usercredentials("iamfd", "password");
		authService = new AuthService();
	}

	@Test(description = "Verify if login api is working for user FD", groups = { "api", "regression", "smoke" })
	public void loginApiTest() throws IOException {
		authService.login(credentials).then().log().all().spec(responseSpec_OK()).and()
				.body("message", equalTo("Success")).and().body("data.token", notNullValue()).and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginApiSchema.json"));
	}

}

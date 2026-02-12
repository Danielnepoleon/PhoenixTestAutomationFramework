package com.api.tests;

import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.services.UserService;

public class UserDetailsApiTest {

	private UserService userService;

	@BeforeMethod(description = "Setting up the user service reference")
	public void setUp() {
		userService = new UserService();
	}

	@Test(description = "Verify if user details api response is shown correctly", groups = { "api", "regression",
			"smoke" })
	public void userDetailsApiTest() throws IOException {
		userService.user(Role.FD).then().spec(responseSpec_OK()).and().body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsApiSchema.json"));
	}

}

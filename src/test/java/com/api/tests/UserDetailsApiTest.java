package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.constants.Role;
import static com.api.utils.SpecUtils.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class UserDetailsApiTest {
	@Test(description = "Verify if user details api response is shown correctly", groups= {"api","regression","smoke"})
	public void userDetailsApiTest() throws IOException {
		given().spec(requestSpecWithAuth(Role.FD)).when().get("userdetails").then().spec(responseSpec_OK())
				.and().body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsApiSchema.json"));
	}

}

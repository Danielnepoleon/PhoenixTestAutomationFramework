package com.api.tests;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;

import com.api.constants.Role;
import com.api.utils.AuthTokenGenerator;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtils;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsApiTest {
	@Test
	public void userDetailsApiTest() throws IOException {
		given().spec(SpecUtils.requestSpecWithAuth(Role.FD)).when().get("userdetails").then().spec(SpecUtils.responseSpec())
				.and().body("message", equalTo("Success")).and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsApiSchema.json"));
	}

}

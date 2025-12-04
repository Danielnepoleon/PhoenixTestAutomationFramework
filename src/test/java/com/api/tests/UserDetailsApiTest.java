package com.api.tests;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.utils.AuthTokenGenerator;
import com.api.utils.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsApiTest {
	@Test
	public void userDetailsApiTest() throws IOException {
		Header header = new Header("Authorization", AuthTokenGenerator.getToken(FD));
		
		given().baseUri(ConfigManager.getProperty("BASE_URI"))
		.and().header(header)
		.and().accept(ContentType.JSON).log().all().when().get("userdetails").then().log().all().statusCode(200)
				.and().body("message", equalTo("Success")).and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsApiSchema.json"));
	}

}

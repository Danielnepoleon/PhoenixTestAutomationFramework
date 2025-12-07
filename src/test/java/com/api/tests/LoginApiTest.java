package com.api.tests;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.pojo.Usercredentials;
import com.api.utils.SpecUtils;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginApiTest {
	Usercredentials credentials = new Usercredentials("iamfd" , "password");
	@Test
	public void loginApiTest() throws IOException {
		given().spec(SpecUtils.requestSpecWithAuth(Role.FD))
		.when().post("login")
		.then().log().all()
		.spec(SpecUtils.responseSpec())
		.and().body("message", equalTo("Success"))
		.and().body("data.token", notNullValue())
		.and().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginApiSchema.json"));
	}

}

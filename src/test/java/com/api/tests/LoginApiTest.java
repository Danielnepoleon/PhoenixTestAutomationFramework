package com.api.tests;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.Usercredentials;
import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginApiTest {
	Usercredentials credentials = new Usercredentials("iamfd" , "password");
	@Test
	public void loginApiTest() throws IOException {
		given().baseUri(getProperty("BASE_URI")).and()
		.contentType(ContentType.JSON).and()
		.body(credentials)
		.log().uri()
		.log().headers()
		.log().body()
		.when().post("login")
		.then().log().all()
		.statusCode(200)
		.and().body("message", equalTo("Success"))
		.and().body("data.token", notNullValue())
		.and().time(lessThan(1000L))
		.and().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginApiSchema.json"));
	}

}

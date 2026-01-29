package com.api.tests.datadriven;

import static com.api.utils.SpecUtils.requestSpec;
import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import com.api.request.model.Usercredentials;
import com.dataproviders.DataProviderUtils;

public class LoginApiExcelDataDrivenTest {

	@Test(description = "Verify if login api is working for user FD", groups = { "api", "regression",
			"smoke" }, dataProviderClass = DataProviderUtils.class, dataProvider = "LoginApiExcelDataProvider")
	public void loginApiExcelTest(Usercredentials userCred) {
		given().spec(requestSpec()).body(userCred).when().post("login").then().log().all().spec(responseSpec_OK()).and()
				.body("message", equalTo("Success")).and().body("data.token", notNullValue()).and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginApiSchema.json"));
	}
	
}

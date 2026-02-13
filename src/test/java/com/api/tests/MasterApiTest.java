package com.api.tests;

import static com.api.utils.SpecUtils.responseSpec_OK;
import static com.api.utils.SpecUtils.responseSpec_TEXT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.services.MasterService;
@Listeners(com.listeners.ApiItestListener.class)
public class MasterApiTest {

	private MasterService masterService;

	@BeforeMethod(description = "Setting up the master service reference")
	public void setUp() {
		masterService = new MasterService();
	}

	@Test(description = "Verify if the master api shows response correctly", groups = { "api", "regression", "smoke" })
	public void masterApiTest() throws IOException {
		masterService.master(Role.FD).then().spec(responseSpec_OK()).body("message", equalTo("Success")).and()
				.body("data", notNullValue()).and().body("data", hasKey("mst_oem")).and().body("$", hasKey("message"))
				.body("data.mst_oem.size()", greaterThan(0)).and().body("data.mst_model.size()", greaterThan(0)).and()
				.body("data.mst_oem.id", everyItem(greaterThan(0))).and()
				.body(matchesJsonSchemaInClasspath("response-schema/MasterApiSchema.json"));
	}

	@Test(description = "Verify if master api gives correct status code for the invalid token", groups = { "api",
			"regression", "smoke", "negative" })
	public void masterApi_InvalidToken_Test() throws IOException {
		masterService.masterWithInvalidToken().then().spec(responseSpec_TEXT(415));
	}

}

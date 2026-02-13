package com.api.tests;

import static com.api.utils.SpecUtils.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.Details;
import com.api.services.DashboardService;
@Listeners(com.listeners.ApiItestListener.class)
public class DetailsApiTest {
	private Details details;
	private DashboardService dashboardService;

	@BeforeMethod(description = "Create payload for the login api & Setting up the Dashboard service reference")
	public void setUp() {
		details = new Details("created_today");
		dashboardService = new DashboardService();
	}

	@Test(description = "Verify if details api is working for user FD", groups = { "api", "regression", "smoke" })
	public void detailsApiTest() throws IOException {
		dashboardService.details(Role.FD, details).then().spec(responseSpec_OK()).and()
				.body("message", equalTo("Success")).and().body("data.message", notNullValue());
	}

}

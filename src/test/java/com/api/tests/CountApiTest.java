package com.api.tests;

import static com.api.utils.SpecUtils.responseSpec_OK;
import static com.api.utils.SpecUtils.responseSpec_TEXT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Listeners(com.listeners.ApiItestListener.class)
@Epic("Job Management")
@Feature("Job count details")
public class CountApiTest {

	private DashboardService dashboardService;

	@BeforeMethod(description = "Setting up the Dashboard service reference")
	public void setUp() {
		dashboardService = new DashboardService();
	}

	@Story("Job details count should be shown")
	@Description("Verify if the count api shows response correctly")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if the count api shows response correctly", groups = { "api", "regression", "smoke" })
	public void countApiTest() {
		dashboardService.count(Role.FD).then().log().body().spec(responseSpec_OK()).body("message", equalTo("Success"))
				.and().body("data", notNullValue()).and().body("data.size()", greaterThanOrEqualTo(3)).and()
				.body("data.count", everyItem(greaterThanOrEqualTo(0))).and()
				.body("data.label", everyItem(not(blankOrNullString()))).and()
				.body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"))
				.and().body(matchesJsonSchemaInClasspath("response-schema/CountApiSchema.json"));
	}

	@Test(description = "Verify if count api gives correct status code for the invalid token", groups = { "api",
			"regression", "smoke", "negative" })
	public void countApiTestwithoutHeader() {
		dashboardService.countWithoutToken().then().spec(responseSpec_TEXT(401));
	}
}

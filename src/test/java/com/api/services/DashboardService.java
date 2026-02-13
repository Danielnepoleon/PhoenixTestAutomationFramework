package com.api.services;

import static com.api.utils.SpecUtils.requestSpec;
import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static com.api.utils.SpecUtils.requestSpecWithAuthBody;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import com.api.request.model.Details;

import io.restassured.response.Response;

public class DashboardService {
	private static final String COUNT_ENDPOINT = "/dashboard/count";
	private static final String DETAILS_ENDPOINT = "/dashboard/details";
	private static final Logger LOGGER = LogManager.getLogger(DashboardService.class);

	public Response count(Role role) {
		LOGGER.info("Making reuest to count api {} for the role {}", COUNT_ENDPOINT, role);
		return given().spec(requestSpecWithAuth(role)).when().get(COUNT_ENDPOINT);
	}

	public Response countWithoutToken() {
		LOGGER.info("Making reuest to count api {} with no auth token", COUNT_ENDPOINT);
		return given().spec(requestSpec()).and().when().get(COUNT_ENDPOINT);
	}

	public Response details(Role role, Details detailsPayload) {
		LOGGER.info("Making reuest to details api {} for the role {} with payload {}", DETAILS_ENDPOINT, role, detailsPayload);
		return given().spec(requestSpecWithAuthBody(role, detailsPayload)).when().post(DETAILS_ENDPOINT);
	}

}

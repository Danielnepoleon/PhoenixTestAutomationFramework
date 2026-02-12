package com.api.services;

import static com.api.utils.SpecUtils.requestSpec;
import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static com.api.utils.SpecUtils.requestSpecWithAuthBody;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;
import com.api.request.model.Details;

import io.restassured.response.Response;

public class DashboardService {
	private static final String COUNT_ENDPOINT = "/dashboard/count";
	private static final String DETAILS_ENDPOINT = "/dashboard/details";

	public Response count(Role role) {

		return given().spec(requestSpecWithAuth(role)).when().get(COUNT_ENDPOINT);
	}

	public Response countWithoutToken() {
		return given().spec(requestSpec()).and().when().get(COUNT_ENDPOINT);
	}

	public Response details(Role role, Details details) {

		return given().spec(requestSpecWithAuthBody(role, details)).when().post(DETAILS_ENDPOINT);
	}

}

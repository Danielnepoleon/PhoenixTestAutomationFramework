package com.api.services;

import static com.api.utils.ConfigManager.getProperty;
import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;

import io.restassured.response.Response;

public class MasterService {

	private static final String MASTER_ENDPOINT = "/master";

	public Response master(Role role) {

		return given().spec(requestSpecWithAuth(role)).when().post(MASTER_ENDPOINT);
	}
	
	public Response masterWithInvalidToken() {
		return given().baseUri(getProperty("BASE_URI")).and().header("Authorization", "3425gdhsdbsj").and().log().all().when()
				.post(MASTER_ENDPOINT);
	}
}

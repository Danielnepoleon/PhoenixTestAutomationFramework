package com.api.services;

import static com.api.utils.ConfigManager.getProperty;
import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;

import io.restassured.response.Response;

public class MasterService {

	private static final String MASTER_ENDPOINT = "/master";
	private static final Logger LOGGER = LogManager.getLogger(MasterService.class);

	public Response master(Role role) {
		LOGGER.info("Making reuest to master api {} for the role {}", MASTER_ENDPOINT, role);
		return given().spec(requestSpecWithAuth(role)).when().post(MASTER_ENDPOINT);
	}

	public Response masterWithInvalidToken() {
		LOGGER.info("Making reuest to master api {} with invalid auth token", MASTER_ENDPOINT);
		return given().baseUri(getProperty("BASE_URI")).and().header("Authorization", "3425gdhsdbsj").and().log().all()
				.when().post(MASTER_ENDPOINT);
	}
}

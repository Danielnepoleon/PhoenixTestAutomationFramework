package com.api.services;

import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserService {

	private static final String USER_DETAILS_ENDPOINT = "userdetails";
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	@Step("User details Api request for the role ")
	public Response user(Role role) {
		LOGGER.info("Making request to user details api {} for the role {}", USER_DETAILS_ENDPOINT, role);
		return given().spec(requestSpecWithAuth(role)).when().get(USER_DETAILS_ENDPOINT);
	}
}

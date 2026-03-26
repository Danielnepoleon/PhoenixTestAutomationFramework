package com.api.services;

import static com.api.utils.SpecUtils.requestSpec;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.Usercredentials;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthService {

	private static final String LOGIN_ENDPOINT = "login";
	private static final Logger LOGGER = LogManager.getLogger(AuthService.class);

	@Step("Login with the credential : {credentials}")
	public Response login(Object credentials) {
		LOGGER.info("Making login reuest for the payload {}", ((Usercredentials) credentials).username());
		return given().spec(requestSpec()).body(credentials).when().post(LOGIN_ENDPOINT);
	}

}

package com.api.services;

import static com.api.utils.SpecUtils.requestSpec;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class AuthService {

	private static final String LOGIN_ENDPOINT = "login";

	public Response login(Object credentials) {

		return given().spec(requestSpec()).body(credentials).when().post(LOGIN_ENDPOINT);
	}

}

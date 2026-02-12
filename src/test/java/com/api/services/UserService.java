package com.api.services;

import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;

import io.restassured.response.Response;

public class UserService {

	private static final String USER_DETAILS_ENDPOINT = "userdetails";

	public Response user(Role role) {

		return given().spec(requestSpecWithAuth(role)).when().get(USER_DETAILS_ENDPOINT);
	}
}

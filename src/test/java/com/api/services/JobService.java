package com.api.services;

import static com.api.utils.SpecUtils.requestSpec;
import static com.api.utils.SpecUtils.requestSpecWithAuthBody;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;

import io.restassured.response.Response;

public class JobService {

	private static final String CREATE_JOB_ENDPOINT = "/job/create";

	public Response createJob(Role role, CreateJobPayload createJobPayload) {

		return given().spec(requestSpecWithAuthBody(role, createJobPayload)).when().post(CREATE_JOB_ENDPOINT);
	}

	public Response createJobWithoutToken() {
		return given().spec(requestSpec()).and().when().get(CREATE_JOB_ENDPOINT);
	}

}

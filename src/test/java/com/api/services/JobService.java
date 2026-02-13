package com.api.services;

import static com.api.utils.SpecUtils.requestSpec;
import static com.api.utils.SpecUtils.requestSpecWithAuthBody;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;

import io.restassured.response.Response;

public class JobService {

	private static final String CREATE_JOB_ENDPOINT = "/job/create";
	private static final Logger LOGGER = LogManager.getLogger(JobService.class);

	public Response createJob(Role role, CreateJobPayload createJobPayload) {
		LOGGER.info("Making reuest to create job api {} for the role {}", CREATE_JOB_ENDPOINT, role);
		return given().spec(requestSpecWithAuthBody(role, createJobPayload)).when().post(CREATE_JOB_ENDPOINT);
	}

	public Response createJobWithoutToken() {
		LOGGER.info("Making reuest to create job api {} with no auth token", CREATE_JOB_ENDPOINT);
		return given().spec(requestSpec()).and().when().get(CREATE_JOB_ENDPOINT);
	}

}

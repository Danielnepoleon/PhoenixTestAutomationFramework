package com.api.tests;

import static com.api.utils.SpecUtils.requestSpecWithAuthBody;
import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;
import com.api.utils.FakerDataGenerator;

public class CreateJobApiTest2 {

	private CreateJobPayload createJobPayload;

	@BeforeMethod(description = "Creating payload for create job API")
	public void setUp() {
		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
	}

	@Test(description = "Verify if the create job api is able to create Inwarranty job", groups = { "api", "regression",
			"smoke" })
	public void createJobApiTest() throws IOException {
		given().spec(requestSpecWithAuthBody(Role.FD, createJobPayload)).when().post("/job/create").then()
				.spec(responseSpec_OK()).body(matchesJsonSchemaInClasspath("response-schema/CreateJobApiSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.job_number", startsWith("JOB_"));

	}

}

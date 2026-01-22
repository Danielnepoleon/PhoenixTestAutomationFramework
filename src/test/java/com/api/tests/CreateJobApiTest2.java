package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Models;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Products;
import com.api.constants.Role;
import com.api.constants.ServiceLocation;
import com.api.constants.WARRANTY_STATUS;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.FakerDataGenerator;

import static com.api.utils.SpecUtils.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobApiTest2 {

	private CreateJobPayload createJobPayload;

	@BeforeMethod(description = "Creating payload for create job API")
	public void setUp() {
		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
	}

	@Test(description = "Verify if the create job api is able to create Inwarranty job", groups = { "api", "regression", "smoke" })
	public void createJobApiTest() throws IOException {
		given().spec(requestSpecWithAuthBody(Role.FD, createJobPayload)).when().post("/job/create").then()
				.spec(responseSpec_OK()).body(matchesJsonSchemaInClasspath("response-schema/CreateJobApiSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.job_number", startsWith("JOB_"));

	}

}

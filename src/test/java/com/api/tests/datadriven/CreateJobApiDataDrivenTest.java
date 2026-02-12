package com.api.tests.datadriven;

import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;
import com.api.services.JobService;
import com.dataproviders.DataProviderUtils;

public class CreateJobApiDataDrivenTest {

	private JobService jobService;

	@BeforeMethod(description = "Setting up the job service reference")
	public void setUp() {
		jobService = new JobService();
	}

	@Test(description = "Verify if the create job api is able to create Inwarranty job", groups = { "api", "regression",
			"smoke" }, dataProviderClass = DataProviderUtils.class, dataProvider = "CreateJobDataProvider")
	public void createJobApiTest(CreateJobPayload createJobPayload) throws IOException {
		jobService.createJob(Role.FD, createJobPayload).then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobApiSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.job_number", startsWith("JOB_"));

	}

}

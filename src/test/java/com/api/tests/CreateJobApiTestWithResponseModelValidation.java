package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
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
import com.api.responsemodel.CreateJobResponseModel;
import com.api.services.JobService;

import io.restassured.response.Response;

public class CreateJobApiTestWithResponseModelValidation {

	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private JobService jobService;

	@BeforeMethod(description = "Creating payload for create job API and Instantiating Job service reference")
	public void setUp() {
		customerAddress = new CustomerAddress("6b", "apart", "Thisara perera", "cbi office", "puluk", 621232, "India",
				"Bihar");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "10000771917300",
				"10000771017301", "10000071817302", getTimeWithDaysAgo(10), Products.NEXUS_2.getCode(),
				Models.GALLEXY.getCode());
		Problems problems = new Problems(Problem.OVERHEATING.getCode(), "Poor battery");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);
		customer = new Customer("Ayush", "Mhatre", "1243546789", "", "wery@gmail.com", "");
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), WARRANTY_STATUS.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemsList);
		jobService = new JobService();
	}

	@Test(description = "Verify if the create job api is able to create Inwarranty job", groups = { "api", "regression",
			"smoke" })
	public void createJobApiTest() throws IOException {
		Response response = jobService.createJob(Role.FD, createJobPayload).then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobApiSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.job_number", startsWith("JOB_"))
				.extract().response();
		CreateJobResponseModel createJobResponseModel = response.as(CreateJobResponseModel.class);

		Assert.assertEquals(createJobPayload.mst_service_location_id(),
				createJobResponseModel.getData().getMst_service_location_id());
		Assert.assertEquals(createJobPayload.mst_platform_id(), createJobResponseModel.getData().getMst_platform_id());
		Assert.assertEquals(createJobPayload.mst_warrenty_status_id(),
				createJobResponseModel.getData().getMst_warrenty_status_id());
		Assert.assertEquals(createJobPayload.mst_oem_id(), createJobResponseModel.getData().getMst_oem_id());
	}

}
